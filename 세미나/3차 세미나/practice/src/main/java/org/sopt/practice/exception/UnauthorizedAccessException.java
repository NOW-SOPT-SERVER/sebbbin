package org.sopt.practice.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message); // 부모 클래스(RuntimeException)의 생성자를 호출
    }
}
