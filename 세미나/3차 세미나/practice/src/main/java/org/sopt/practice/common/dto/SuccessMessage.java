package org.sopt.practice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    BLOG_CREATE_SUCCESS(HttpStatus.CREATED.value(),"블로그 생성이 완료되었습니다."),
    POST_CREATE_SUCCESS(HttpStatus.CREATED.value(), "글 작성이 완료되었습니다."),
    POST_ALL_FIND_SUCCESS(HttpStatus.OK.value(), "작성된 글을 모두 불러왔습니다."),
    POST_FIND_SUCCESS(HttpStatus.OK.value(), "요청한 글을 불러왔습니다."),

    ;
    private final int status;
    private final String message;
}
