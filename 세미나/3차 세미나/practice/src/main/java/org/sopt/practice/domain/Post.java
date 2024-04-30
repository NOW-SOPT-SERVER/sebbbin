package org.sopt.practice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter

@NoArgsConstructor
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Blog blog;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    private String title;

    private String content;

    public static Post create(Blog blog, Member member, String title, String content) {
        return new Post(blog,member, title, content);
    }

    public Post(Blog blog, Member member, String title, String content) {
        this.blog = blog;
        this.member = member;
        this.title = title;
        this.content = content;
    }
}

