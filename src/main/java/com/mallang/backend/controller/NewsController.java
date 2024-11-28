package com.mallang.backend.controller;

import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 건강매거진 전체 조회 (모든 사용자 접근 가능)
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    // 특정 ID 건강매거진 조회 (모든 사용자 접근 가능)
    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        NewsDTO newsDTO = newsService.getNewsById(id);
        if (newsDTO != null) {
            return ResponseEntity.ok(newsDTO);
        } else {
            return ResponseEntity.badRequest().body("해당 건강매거진을 찾을 수 없습니다.");
        }
    }

    // 건강매거진 작성 (관리자 전용)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNews(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String password
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

        NewsDTO newsDTO = NewsDTO.builder()
                .title(title)
                .content(content)
                .password(password)
                .build();

        NewsDTO savedNews = newsService.createNews(newsDTO);
        return ResponseEntity.ok("등록이 완료되었습니다.");
    }

    // 건강매거진 수정 (관리자 전용)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateNews(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String password
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

        NewsDTO newsDTO = NewsDTO.builder()
                .title(title)
                .content(content)
                .password(password)
                .build();

        boolean isUpdated = newsService.updateNewsById(id, newsDTO);

        if (isUpdated) {
            return ResponseEntity.ok("수정이 완료되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("해당 건강매거진을 찾을 수 없습니다. 수정에 실패하였습니다.");
        }
    }

    // 건강매거진 삭제 (관리자 전용)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        boolean isDeleted = newsService.deleteNewsById(id);

        if (isDeleted) {
            return ResponseEntity.ok("건강매거진이 성공적으로 삭제되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("해당 건강매거진을 찾을 수 없습니다. 삭제에 실패하였습니다.");
        }
    }
}