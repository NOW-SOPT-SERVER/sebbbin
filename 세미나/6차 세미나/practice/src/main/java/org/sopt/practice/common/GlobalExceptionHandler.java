package org.sopt.practice.common;


import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.dto.ErrorResponse;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


//발생한 에러 잡아서 정의한 에러로 할 수 있도록
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException (NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION));
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> handlerUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(e.getErrorMessage().getStatus(), e.getErrorMessage().getMessage()));
    }

}
