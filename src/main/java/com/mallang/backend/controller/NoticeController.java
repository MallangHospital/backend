package com.mallang.backend.controller;

import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 전체 조회 (모든 사용자 접근 가능)
    @GetMapping
    public ResponseEntity<?> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    // 특정 ID 공지사항 조회 (모든 사용자 접근 가능)
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticeById(@PathVariable Long id) {
        NoticeDTO noticeDTO = noticeService.getNoticeById(id);
        if (noticeDTO != null) {
            return ResponseEntity.ok(noticeDTO);
        } else {
            return ResponseEntity.badRequest().body("해당 공지사항을 찾을 수 없습니다.");
        }
    }

    // 공지사항 작성 (관리자 전용)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // 관리자 권한 확인
    public ResponseEntity<?> createNotice(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String password,
            @RequestParam(required = false) String noticeWriter
    ) {
        if (title.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("제목이 입력되지 않았습니다. 다시 확인해 주세요.");
        }

        if (content.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("본문란이 비어 있습니다. 내용을 입력해 주세요.");
        }

        if (password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("비밀번호를 다시 확인해주세요.");
        }

        NoticeDTO noticeDTO = NoticeDTO.builder()
                .title(title)
                .content(content)
                .password(password)
                .noticeWriter(noticeWriter != null ? noticeWriter : "관리자")
                .build();

        NoticeDTO savedNotice = noticeService.createNotice(noticeDTO);
        return ResponseEntity.ok("공지사항이 성공적으로 등록되었습니다! ID: " + savedNotice.getId());
    }

    // 공지사항 삭제 (관리자 전용)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // 관리자 권한 확인
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) {
        boolean isDeleted = noticeService.deleteNoticeById(id);

        if (isDeleted) {
            return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("해당 공지사항을 찾을 수 없습니다. 삭제에 실패하였습니다.");
        }
    }

    // 공지사항 수정 (관리자 전용)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // 관리자 권한 확인
    public ResponseEntity<?> updateNotice(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String password,
            @RequestParam(required = false) String noticeWriter
    ) {
        if (title.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("제목이 입력되지 않았습니다. 다시 확인해 주세요.");
        }

        if (content.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("본문란이 비어 있습니다. 내용을 입력해 주세요.");
        }

        if (password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("비밀번호를 다시 확인해주세요.");
        }

        NoticeDTO noticeDTO = NoticeDTO.builder()
                .title(title)
                .content(content)
                .password(password)
                .noticeWriter(noticeWriter != null ? noticeWriter : "관리자")
                .build();

        boolean isUpdated = noticeService.updateNoticeById(id, noticeDTO);

        if (isUpdated) {
            return ResponseEntity.ok("공지사항이 성공적으로 수정되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("해당 공지사항을 찾을 수 없습니다. 수정에 실패하였습니다.");
        }
    }
}