package org.sopt.practice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.domain.Member;
import org.sopt.practice.dto.member.MemberCreateDto;
import org.sopt.practice.dto.member.MemberFindDto;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //의존성 주입
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional //DB변경사항을 반영할 때 사용
    public String createMember(MemberCreateDto memberCreateDto)
    {
        Member member = memberRepository.save(Member.create(memberCreateDto.name(), memberCreateDto.part(), memberCreateDto.age()));
        return member.getId().toString();
    }
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

}
