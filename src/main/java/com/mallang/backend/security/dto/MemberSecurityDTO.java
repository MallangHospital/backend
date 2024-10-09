package com.mallang.backend.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class MemberSecurityDTO extends User {
    private String mid;
    private String mpw;
    private String email;
    private String name;
    private String phoneNum;
    private boolean del;

    public MemberSecurityDTO(String username, String password, String email, String name, String phoneNum, boolean del, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.name = name;
        this.phoneNum = phoneNum;
        this.del = del;
    }
}
