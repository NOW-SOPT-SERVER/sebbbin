package org.sopt.practice.controller;


import lombok.RequiredArgsConstructor;
import org.sopt.practice.dto.member.MemberCreateDto;
import org.sopt.practice.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    //새로운 멤버를 추가하는 api
    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberCreateDto memberCreateDto) {
        return ResponseEntity.created(URI.create(memberService.createMember(memberCreateDto))).build();
        //memberService의 createMember를 이용해 생성해주도록한다.

    }


}
