package org.sopt.practice.dto.product;

public record ProductAddDto(Long memberId, String title, int price, String description, String address) {
}
