package org.sopt.practice.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.dto.*;
import org.sopt.practice.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController //Contorller라는 것을 알려주기 위해
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

//    @PostMapping
//    public ResponseEntity postMember(@RequestBody MemberCreateDto memberCreateDto)
//    {
//        return ResponseEntity.created(URI.create(memberService.createMember(memberCreateDto))).build();
//    }

    //ResponseEntity는 generic타입을 넣을 수 있음

    @GetMapping("/members/{memberId}")
        public ResponseEntity<MemberFindDto> findMemberById(@PathVariable Long memberId){
            return ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity deleteMemberById(@PathVariable Long memberId){
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/members")
    public ResponseEntity<UserJoinResponse> postMember(
            @RequestBody MemberCreateDto memberCreate
    ) {
        UserJoinResponse userJoinResponse = memberService.createMember(memberCreate);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", userJoinResponse.userId())
                .body(
                        userJoinResponse
                );
    }

//    @PostMapping("/members/login")
//    public ResponseEntity<TokenDto> login(@RequestBody MemberLoginRequest memberLoginRequest) {
//        return ResponseEntity.ok(memberService.login(memberLoginRequest));
//    }
}
