package org.sopt.practice.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.domain.Member;
import org.sopt.practice.dto.MemberCreateDto;
import org.sopt.practice.dto.MemberFindDto;
import org.sopt.practice.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<MemberFindDto> findMemberList() {
        // DB에서 멤버 리스트를 가져온 후 DTO로 변환
        return memberRepository.findAll().stream()
                .map(member -> new MemberFindDto(member.getName(),member.getPart(), member.getAge()))
                .collect(Collectors.toList());
    }

    public MemberFindDto findMemberById(Long memberId){
        return MemberFindDto.of(memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다.")
        ));
    }
    @Transactional //삭제도 DB변경사항 적용이라
    //아무것도 return 하지 않아서 void
    public void deleteMemberById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        memberRepository.delete(member);
    }

}
