package com.mallang.backend.controller;

import com.mallang.backend.dto.MemberJoinDTO;
import com.mallang.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    // 회원가입 요청 처리
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinDTO memberJoinDTO) {
        try {
            memberService.join(memberJoinDTO);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 아이디 중복 확인
    @GetMapping("/check-id/{mid}")
    public ResponseEntity<String> checkId(@PathVariable String mid) {
        if (memberService.isDuplicateId(mid)) {
            return ResponseEntity.badRequest().body("아이디가 중복되었습니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        }
    }
}