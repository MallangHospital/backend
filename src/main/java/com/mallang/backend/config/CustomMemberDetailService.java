package com.mallang.backend.config;

import com.mallang.backend.domain.Member;
import com.mallang.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
        Member memberData = memberRepository.findById(mid).get();
        if (memberData != null) {
            return new CustomMemberDetails(memberData);
        }
        return null;
    }
}
