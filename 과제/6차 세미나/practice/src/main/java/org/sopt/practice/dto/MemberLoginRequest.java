package org.sopt.practice.dto;

import lombok.Getter;
import lombok.Setter;
import org.sopt.practice.domain.Part;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class MemberLoginRequest {
    private String name;
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(name, password);
    }
}
