package org.sopt.practice.dto;

import org.sopt.practice.domain.Part;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record MemberLoginRequest(String name, String password) {
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(name, password);
    }
}
