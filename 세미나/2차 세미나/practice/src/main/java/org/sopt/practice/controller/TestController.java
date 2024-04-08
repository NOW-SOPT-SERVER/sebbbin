package org.sopt.practice.controller;

import org.sopt.practice.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test(){
        return "test api";
    }

    //Json 통신 테스트
    @GetMapping("/json")
    public ApiResponse testJson(){
        return ApiResponse.create("testAPI with JSON");
    }
}
