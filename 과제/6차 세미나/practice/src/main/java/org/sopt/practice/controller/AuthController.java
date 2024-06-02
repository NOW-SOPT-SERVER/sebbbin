package org.sopt.practice.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.dto.TokenDto;
import org.sopt.practice.dto.TokenRequest;
import org.sopt.practice.dto.TokenResponse;
import org.sopt.practice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/reissue")
    public ResponseEntity<?> reissueAccessToken(@RequestBody TokenRequest tokenRequest) {
        try {
            String newAccessToken = authService.reissueAccessToken(tokenRequest.refreshToken(), tokenRequest.memberId());
            return ResponseEntity.ok(new TokenResponse(newAccessToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
