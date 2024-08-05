package org.sopt.practice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//엔티티 클래스
@Entity
@Getter
@NoArgsConstructor
public class Blog extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) //테이블 조회시 우선 조회가 되지 않음
    private Member member;

    @Column(length = 200)
    private String title;

    private String description;
    private String imageUrl;

    private Blog(Member member, String title,String imageUrl, String description){
        this.member = member;
        this.title = title;
        this.description= description;
        this.imageUrl = imageUrl;
    }

    public static Blog create(Member member, String title, String imageUrl, String description){
        return new Blog(member, title,imageUrl, description);
    }
    public void updateTitle(
            String title
    ) {
        this.title = title;
    }


}
