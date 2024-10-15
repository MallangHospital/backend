package com.mallang.backend.service;

import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Role;
import com.mallang.backend.dto.MemberJoinDTO;
import com.mallang.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberJoinDTO memberJoinDTO) {
        // 약관 동의 확인
        if (!memberJoinDTO.isAgreeToTerms()) {
            throw new IllegalArgumentException("약관에 동의해야 합니다.");
        }

        // 아이디 중복 확인
        if (memberRepository.existsById(memberJoinDTO.getMid())) {
            throw new IllegalArgumentException("아이디가 이미 존재합니다.");
        }

        // 비밀번호 형식 검사
        if (!isValidPassword(memberJoinDTO.getMpw())) {
            throw new IllegalArgumentException("비밀번호가 형식에 맞지 않습니다.");
        }

        // 전화번호 형식 검사
        if (!isValidPhoneNumber(memberJoinDTO.getPhoneNum())) {
            throw new IllegalArgumentException("전화번호가 형식에 맞지 않습니다.");
        }

        // 이메일 형식 검사
        if (!isValidEmail(memberJoinDTO.getEmail())) {
            throw new IllegalArgumentException("이메일이 형식에 맞지 않습니다.");
        }

        // 모든 필수 항목 입력 여부 확인
        if (memberJoinDTO.getMid() == null || memberJoinDTO.getMpw() == null || memberJoinDTO.getName() == null ||
                memberJoinDTO.getEmail() == null || memberJoinDTO.getPhoneNum() == null) {
            throw new IllegalArgumentException("모든 항목이 입력되지 않았습니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberJoinDTO.getMpw());
        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(encodedPassword);

        // 아이디가 'admin'으로 시작하면 ROLE_ADMIN, 그렇지 않으면 ROLE_MEMBER 설정
        if (member.getMid().startsWith("admin")) {
            member.changeRole(Role.ROLE_ADMIN);  // 관리자 권한 설정
        } else {
            member.changeRole(Role.ROLE_MEMBER);  // 기본 회원 권한 설정
        }

        memberRepository.save(member);
    }



    // 비밀번호 형식 검사 로직
    private boolean isValidPassword(String password) {
        // 비밀번호 형식 조건: 최소 8글자
        return password.length() >= 8;
    }
    // 전화번호 형식 검사 로직
    private boolean isValidPhoneNumber(String phoneNum) {
        // 전화번호 형식: 010-1234-5678 형태
        return phoneNum.matches("^010-\\d{4}-\\d{4}$");
    }

    // 이메일 형식 검사 로직
    private boolean isValidEmail(String email) {
        // 이메일 형식 확인: 대략적인 형식 검사
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // 아이디 중복 검사 메서드
    public boolean isDuplicateId(String mid) {
        // 해당 아이디가 이미 존재하는지 확인
        return memberRepository.existsById(mid);
    }
}