package org.sopt.practice.dto.member;

import org.sopt.practice.domain.Part;

public record MemberCreateDto(String name, Part part, int age){

}
