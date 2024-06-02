package org.sopt.practice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.dto.ErrorResponse;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.domain.Member;
import org.sopt.practice.domain.RefreshToken;
import org.sopt.practice.dto.*;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //의존성 주입
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserJoinResponse createMember(
            MemberCreateDto memberCreate
    ) {
        // 비밀번호 해싱
        String encodedPassword = passwordEncoder.encode(memberCreate.password());

        Member member = memberRepository.save(
                Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age(), encodedPassword)
        );
        Long memberId = member.getId();
        String accessToken = jwtTokenProvider.issueAccessToken(
                UserAuthentication.createUserAuthentication(memberId)
        );
        return UserJoinResponse.of(accessToken, memberId.toString());
    }
//    @Transactional //DB변경사항을 반영할 때 사용
//    public String createMember(MemberCreateDto memberCreateDto)
//    {
//        Member member = memberRepository.save(Member.create(memberCreateDto.name(), memberCreateDto.part(), memberCreateDto.age()));
//        return member.getId().toString();
//    }
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION)
        );
    }

    public MemberFindDto findMemberById(Long memberId){
        return MemberFindDto.of(memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION)
        ));
    }
    @Transactional //삭제도 DB변경사항 적용이라
    //아무것도 return 하지 않아서 void
    public void deleteMemberById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->  new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION));
        memberRepository.delete(member);
    }

    @Transactional
    public TokenDto login(MemberLoginRequest memberLoginRequest) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginRequest.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

}
