package org.sopt.practice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.domain.Member;
import org.sopt.practice.dto.member.MemberCreateDto;
import org.sopt.practice.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional //DB변경사항을 반영할 때 사용
    public String createMember(MemberCreateDto memberCreateDto)
    {
        Member member = memberRepository.save(Member.create(memberCreateDto.name(), memberCreateDto.age()));
        return member.getId().toString();
    }


}
