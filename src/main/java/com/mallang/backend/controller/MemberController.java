package com.mallang.backend.controller;

import com.mallang.backend.dto.MemberJoinDTO;
import com.mallang.backend.dto.PostDTO;
import com.mallang.backend.service.PostService;
import com.mallang.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @RestController // 변경: @Controller -> @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/posts") // 변경: RESTful 경로 설정
    public static class PostController {

        private final PostService postService;

        @GetMapping
        public ResponseEntity<List<PostDTO>> findAll() {
            List<PostDTO> postDTOList = postService.findAll();
            return ResponseEntity.ok(postDTOList); // HTTP 200 응답 반환
        }

        @GetMapping("/{id}")
        public ResponseEntity<PostDTO> findById(@PathVariable Long id) {
            PostDTO postDTO = postService.findById(id);
            return ResponseEntity.ok(postDTO); // HTTP 200 응답 반환
        }

        @PostMapping
        public ResponseEntity<PostDTO> save(@RequestBody PostDTO postDTO) { // @RequestBody 사용
            PostDTO savedPost = postService.save(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost); // HTTP 201 응답 반환
        }

        @PutMapping("/{id}")
        public ResponseEntity<PostDTO> update(@PathVariable Long id, @RequestBody PostDTO postDTO) {
            postDTO.setId(id); // 요청 URL에서 ID 설정
            PostDTO updatedPost = postService.update(postDTO);
            return ResponseEntity.ok(updatedPost); // HTTP 200 응답 반환
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            postService.delete(id);
            return ResponseEntity.noContent().build(); // HTTP 204 응답 반환
        }
    }
}