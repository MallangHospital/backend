package com.mallang.backend.service;

import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Role;
import com.mallang.backend.dto.MemberJoinDTO;
import com.mallang.backend.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ModelMapper modelMapper;
    private MemberJoinDTO memberJoinDTO;

    @BeforeEach
    void setUp() {
        memberJoinDTO = MemberJoinDTO.builder()
                .mid("testUser")
                .mpw("password123")
                .email("test@example.com")
                .name("Test User")
                .phoneNum("010-1234-5678")
                .rrn("0202022012432")
                .agreeToTerms(true)
                .build();
    }

    @Test
    void testJoin_Success() {
        // given
        when(memberRepository.existsById(memberJoinDTO.getMid())).thenReturn(false);  // 아이디 중복 검사 모킹
        when(passwordEncoder.encode(memberJoinDTO.getMpw())).thenReturn("encodedPassword");  // 비밀번호 암호화 모킹
        when(modelMapper.map(any(MemberJoinDTO.class), eq(Member.class))).thenReturn(new Member());  // ModelMapper 모킹

        // when
        memberService.join(memberJoinDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));  // 회원 정보가 저장되었는지 확인
    }

    @Test
    void testJoin_DuplicateId() {
        // given
        when(memberRepository.existsById(memberJoinDTO.getMid())).thenReturn(true);  // 아이디 중복 검사 모킹

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.join(memberJoinDTO);  // 중복된 아이디로 가입 시도
        });

        // then
        assertEquals("아이디가 이미 존재합니다.", exception.getMessage());  // 예외 메시지 확인
        verify(memberRepository, never()).save(any(Member.class));  // 중복된 아이디일 경우 저장되지 않음
    }

    @Test
    void testIsDuplicateId_True() {
        // given
        when(memberRepository.existsById("testUser")).thenReturn(true);  // 모킹된 아이디 중복 검사

        // when
        boolean result = memberService.isDuplicateId("testUser");

        // then
        assertTrue(result);  // 아이디가 중복되었을 때 true 반환 확인
    }

    @Test
    void testIsDuplicateId_False() {
        // given
        when(memberRepository.existsById("newUser")).thenReturn(false);  // 중복되지 않은 아이디

        // when
        boolean result = memberService.isDuplicateId("newUser");

        // then
        assertFalse(result);  // 아이디가 중복되지 않았을 때 false 반환 확인
    }
}