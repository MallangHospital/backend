package com.mallang.backend.controller;

import com.mallang.backend.domain.Notice;
import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.repository.NoticeRepository;
import com.mallang.backend.service.NoticeService;
import com.mallang.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {



    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;


    // 공지사항 전체 조회
    @GetMapping
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    // 특정 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticeById(@PathVariable Long id) {
        NoticeDTO noticeDTO = noticeService.getNoticeById(id);
        if (noticeDTO != null) {
            return ResponseEntity.ok(noticeDTO);
        } else {
            return ResponseEntity.badRequest().body("해당 공지사항을 찾을 수 없습니다.");
        }
    }

    // 공지사항 작성
    @PostMapping
    public ResponseEntity<?> createNotice(@RequestBody NoticeDTO noticeDTO) {
        // Validation
        if (noticeDTO.getTitle() == null || noticeDTO.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("제목을 입력해주세요.");
        }
        if (noticeDTO.getContent() == null || noticeDTO.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("본문을 입력해주세요.");
        }
        if (noticeDTO.getPassword() == null || noticeDTO.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("비밀번호를 입력해주세요.");
        }

        NoticeDTO savedNotice = noticeService.createNotice(noticeDTO);
        return ResponseEntity.ok(savedNotice);
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotice(@PathVariable Long id, @RequestBody NoticeDTO noticeDTO) {
        boolean isUpdated = noticeService.updateNotice(id, noticeDTO);
        if (isUpdated) {
            return ResponseEntity.ok("수정이 완료되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("해당 공지사항을 찾을 수 없습니다. 수정에 실패하였습니다.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(
            @PathVariable Long id,
            @RequestParam("password") String password) {
        try {
            boolean isDeleted = noticeService.deleteNotice(id, password);
            if (isDeleted) {
                return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다!");
            } else {
                return ResponseEntity.badRequest().body("공지사항을 찾을 수 없거나 비밀번호가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }
}