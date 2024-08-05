package org.sopt.practice.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.domain.Member;
import org.sopt.practice.domain.Token;
import org.sopt.practice.dto.*;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.exception.UnauthorizedException;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.repository.RedisTokenRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTokenRepository redisTokenRepository;

    @Transactional
    public UserJoinResponse createMember(MemberCreateDto memberCreate) {
        // 비밀번호 해싱
        String encodedPassword = passwordEncoder.encode(memberCreate.password());
        // Member 저장
        Member member = memberRepository.save(
                Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age(), encodedPassword)
        );
        // Access Token 생성
        Long memberId = member.getId();
        String accessToken = jwtTokenProvider.issueAccessToken(
                UserAuthentication.createUserAuthentication(memberId)
        );
        // Refresh Token 생성
        String refreshToken = jwtTokenProvider.issueRefreshToken(
                UserAuthentication.createUserAuthentication(memberId)
        );
        redisTokenRepository.save(Token.of(memberId, refreshToken));


        return UserJoinResponse.of(accessToken, refreshToken, memberId.toString());
    }

    @Transactional
    public UserJoinResponse refreshToken(Long memberId) {
        if(!redisTokenRepository.existsById(memberId.toString())){
            throw new UnauthorizedException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION);
        }
        findById(memberId);

        String accessToken = jwtTokenProvider.issueAccessToken(
                UserAuthentication.createUserAuthentication(memberId)
        );
        String refreshToken = jwtTokenProvider.issueRefreshToken(
                UserAuthentication.createUserAuthentication(memberId)
        );
        redisTokenRepository.save(Token.of(memberId, refreshToken));
        return UserJoinResponse.of(accessToken, refreshToken, memberId.toString());
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION)
        );
    }

    public MemberFindDto findMemberById(Long memberId) {
        return MemberFindDto.of(memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION)
        ));
    }

    @Transactional
    public void deleteMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION));
        memberRepository.delete(member);
    }

//    @Transactional
//    public TokenDto login(MemberLoginRequest memberLoginRequest) {
//        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
//        UsernamePasswordAuthenticationToken authenticationToken = memberLoginRequest.toAuthentication();
//
//        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
//        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 2. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);
//
//        // 3. RefreshToken 저장
//        RefreshToken refreshToken = RefreshToken.builder()
//                .key(authentication.getName())
//                .value(tokenDto.getRefreshToken())
//                .build();
//
//        refreshTokenRepository.save(refreshToken);
//
//        // 4. 토큰 발급
//        return tokenDto;
//    }

}
