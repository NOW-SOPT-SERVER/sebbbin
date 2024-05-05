package org.sopt.practice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //물건의 아이디 -> 아이디로 물건 구분

    private String title; //제목 : 물건의 이름과 동일 / 근데 제목이라고 적혀있으니 제목으로
    private int price; //가격
    private String description; //자세한 설명 : 묘사
    private String address; //주소
    private boolean sale = true; //확장 가능성을 위한 판매여부 / 초기값 1로 설정 -> 등록하면 일단 판매 시작

    @ManyToOne(fetch = FetchType.LAZY) //fetch = FetchType.LAZY : 멤버 정보가 실제로 필요할 때 로드
    @JoinColumn(name = "member_id") // FK 컬럼 명시 member 테이블과 product 테이블 연결
    private Member member;

    @Builder
    public Product(String title, int price, String description, String address, Member member) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.address = address;
        this.member = member;
    }

    public static Product create(String title, int price, String description, String address) {
        return Product.builder()
                .title(title)
                .price(price)
                .description(description)
                .address(address)
                .build();

    }
}
