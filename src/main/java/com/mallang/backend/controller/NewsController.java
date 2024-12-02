package com.mallang.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Base64;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final ObjectMapper objectMapper;

    // 모든 뉴스 조회
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    // 특정 뉴스 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        NewsDTO newsDTO = newsService.getNewsById(id);
        if (newsDTO != null) {
            return ResponseEntity.ok(newsDTO);
        }
        return ResponseEntity.badRequest().body("해당 뉴스를 찾을 수 없습니다.");
    }

    // 뉴스 등록
    @PostMapping
    public ResponseEntity<?> createNews(
            @RequestPart("newsDTO") String newsDTOString,
            @RequestPart(value = "mainFile", required = false) MultipartFile mainFile,
            @RequestPart(value = "attachment", required = false) MultipartFile attachment
    ) {
        try {
            NewsDTO newsDTO = objectMapper.readValue(newsDTOString, NewsDTO.class);
            String mainFileBase64 = mainFile != null ? encodeFileToBase64(mainFile) : null;
            String attachmentBase64 = attachment != null ? encodeFileToBase64(attachment) : null;
            NewsDTO savedNews = newsService.createNews(newsDTO, mainFileBase64, attachmentBase64);
            return ResponseEntity.ok(savedNews);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 뉴스 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(
            @PathVariable Long id,
            @RequestPart("newsDTO") String newsDTOString,
            @RequestPart(value = "mainFile", required = false) MultipartFile mainFile,
            @RequestPart(value = "attachment", required = false) MultipartFile attachment
    ) {
        try {
            NewsDTO newsDTO = objectMapper.readValue(newsDTOString, NewsDTO.class);
            String mainFileBase64 = mainFile != null ? encodeFileToBase64(mainFile) : null;
            String attachmentBase64 = attachment != null ? encodeFileToBase64(attachment) : null;
            boolean isUpdated = newsService.updateNewsById(id, newsDTO, mainFileBase64, attachmentBase64);
            if (isUpdated) {
                return ResponseEntity.ok("수정이 완료되었습니다!");
            }
            return ResponseEntity.badRequest().body("해당 뉴스를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 뉴스 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        boolean isDeleted = newsService.deleteNewsById(id);
        if (isDeleted) {
            return ResponseEntity.ok("뉴스가 삭제되었습니다!");
        }
        return ResponseEntity.badRequest().body("해당 뉴스를 찾을 수 없습니다.");
    }

    private String encodeFileToBase64(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        return "data:" + file.getContentType() + ";base64," + Base64.getEncoder().encodeToString(fileBytes);
    }
}