package org.sopt.practice.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor //Lombok이 직접 생성자를 추가시켜줌
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;

    @Enumerated(EnumType.STRING) //enum class의 string 값을 가져옴
    private Part part;
    private int age;
    private String password;

    //접근 제어자 private인 이유 : 객체의 무분별한 생성을 막기 위해서

    @Builder
    public Member(String name, Part part, int age, String password){
        this.name = name;
        this.part = part;
        this.age = age;
        this.password = password;
    }

    public static Member create(String name, Part part, int age, String password){
        return Member.builder()
                .name(name)
                .part(part)
                .age(age)
                .password(password)
                .build();
    }

}
