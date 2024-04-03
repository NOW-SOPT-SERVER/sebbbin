package org.sopt.practice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//정적 메서드 반환 생성
@AllArgsConstructor
@Getter
public class ApiResponse {
    private String content;
    public static ApiResponse create(String content){
        return new ApiResponse(content);
    }
}
