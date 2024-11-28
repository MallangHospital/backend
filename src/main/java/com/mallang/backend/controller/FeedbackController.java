package com.mallang.backend.controller;

import com.mallang.backend.dto.FeedbackDTO;
import com.mallang.backend.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // 모든 건의사항 조회 (관리자 전용)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    public ResponseEntity<List<FeedbackDTO>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    // 특정 ID 건의사항 조회 (관리자 전용)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
    public ResponseEntity<?> getFeedbackById(@PathVariable Long id) {
        FeedbackDTO feedback = feedbackService.getFeedbackById(id);
        if (feedback != null) {
            return ResponseEntity.ok(feedback);
        } else {
            return ResponseEntity.badRequest().body("해당 건의사항을 찾을 수 없습니다.");
        }
    }

    // 건의사항 작성 (사용자 전용)
    @PostMapping
    @PreAuthorize("hasRole('USER')") // 사용자만 접근 가능
    public ResponseEntity<?> submitFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        if (feedbackDTO.getTitle() == null || feedbackDTO.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("제목이 입력되지 않았습니다.");
        }
        if (feedbackDTO.getContent() == null || feedbackDTO.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("내용란이 비어 있습니다.");
        }
        if (feedbackDTO.getName() == null || feedbackDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("이름이 입력되지 않았습니다.");
        }
        if (feedbackDTO.getPhoneNumber() == null || feedbackDTO.getPhoneNumber().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("휴대폰 번호가 입력되지 않았습니다.");
        }
        if (feedbackDTO.getEmail() == null || feedbackDTO.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("이메일이 입력되지 않았습니다.");
        }

        FeedbackDTO savedFeedback = feedbackService.submitFeedback(feedbackDTO);
        return ResponseEntity.ok("건의사항이 성공적으로 접수되었습니다! ID: " + savedFeedback.getId());
    }
}