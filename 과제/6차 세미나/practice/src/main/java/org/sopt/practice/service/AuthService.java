package org.sopt.practice.service;

import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.dto.TokenDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public String reissueAccessToken(String refreshToken, String userId) {
        if (tokenService.validateRefreshToken(userId, refreshToken)) {
            // 해당 사용자를 위한 새로운 인증 정보 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 새로운 Access Token 생성
            return jwtTokenProvider.issueAccessToken(authentication);
        } else {
            throw new RuntimeException("유효하지 않은 refresh token");
        }
    }
}
