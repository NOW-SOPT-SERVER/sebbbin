package org.sopt.practice.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.Service.MemberService;
import org.sopt.practice.dto.MemberCreateDto;
import org.sopt.practice.dto.MemberFindDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController //Contorller라는 것을 알려주기 위해
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberCreateDto memberCreateDto)
    {
        return ResponseEntity.created(URI.create(memberService.createMember(memberCreateDto))).build();
    }



    //ResponseEntity는 generic타입을 넣을 수 있음

    @GetMapping("/{memberId}")
        public ResponseEntity<MemberFindDto> findMemberById(@PathVariable Long memberId){
            return ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMemberById(@PathVariable Long memberId){
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }
}
