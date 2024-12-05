package org.sopt.practice.dto;

import org.sopt.practice.domain.Member;
import org.sopt.practice.domain.Part;

//정보만을 가져오는 !!
public record MemberFindDto(
    String name,
    Part part,
    int age
){
    public static MemberFindDto of(Member member) //한 멤버 객체에서 멤버를 찾는다 라는 의미의 of 함수
    {
        return new MemberFindDto(member.getName(), member.getPart(), member.getAge());
    }


}
