package com.mallang.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final ObjectMapper objectMapper;

    // 모든 건강매거진 조회
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    // 특정 ID 건강매거진 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        NewsDTO newsDTO = newsService.getNewsById(id);
        if (newsDTO != null) {
            return ResponseEntity.ok(newsDTO);
        } else {
            return ResponseEntity.badRequest().body("해당 건강매거진을 찾을 수 없습니다.");
        }
    }

    // 건강매거진 작성
    @PostMapping
    public ResponseEntity<?> createNews(
            @RequestParam("newsDTO") String newsDTOString,
            @RequestPart(value = "mainFile", required = false) MultipartFile mainFile,
            @RequestPart(value = "attachment", required = false) MultipartFile attachment) {

        try {
            // JSON -> DTO 변환
            NewsDTO newsDTO = objectMapper.readValue(newsDTOString, NewsDTO.class);

            // Validation
            if (newsDTO.getTitle() == null || newsDTO.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("제목이 입력되지 않았습니다.");
            }
            if (newsDTO.getContent() == null || newsDTO.getContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("본문란이 비어 있습니다.");
            }

            // Service 호출
            NewsDTO savedNews = newsService.createNews(newsDTO, mainFile, attachment);
            return ResponseEntity.ok(savedNews);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("데이터 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 건강매거진 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(
            @PathVariable Long id,
            @RequestParam("newsDTO") String newsDTOString,
            @RequestPart(value = "mainFile", required = false) MultipartFile mainFile,
            @RequestPart(value = "attachment", required = false) MultipartFile attachment) {

        try {
            // JSON -> DTO 변환
            NewsDTO newsDTO = objectMapper.readValue(newsDTOString, NewsDTO.class);

            // Validation
            if (newsDTO.getTitle() == null || newsDTO.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("제목이 입력되지 않았습니다.");
            }
            if (newsDTO.getContent() == null || newsDTO.getContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("본문란이 비어 있습니다.");
            }

            boolean isUpdated = newsService.updateNewsById(id, newsDTO, mainFile, attachment);

            if (isUpdated) {
                return ResponseEntity.ok("수정이 완료되었습니다!");
            } else {
                return ResponseEntity.badRequest().body("해당 건강매거진을 찾을 수 없습니다.");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("데이터 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 건강매거진 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        boolean isDeleted = newsService.deleteNewsById(id);
        if (isDeleted) {
            return ResponseEntity.ok("건강매거진이 성공적으로 삭제되었습니다!");
        } else {
            return ResponseEntity.badRequest().body("해당 건강매거진을 찾을 수 없습니다.");
        }
    }
}