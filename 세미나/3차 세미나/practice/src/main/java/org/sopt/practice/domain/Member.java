package org.sopt.practice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    //판매하는 아이템의 개수를 추가해줘도 좋을거같다.

    @Builder
    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static Member create(String name, int age) {
        return Member.builder()
                .name(name)
                .age(age)
                .build();
    }
}
