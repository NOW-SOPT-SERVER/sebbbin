package org.sopt.practice.service;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.domain.Blog;
import org.sopt.practice.domain.Member;
import org.sopt.practice.domain.Post;
import org.sopt.practice.dto.post.PostCreateRequest;
import org.sopt.practice.exception.CustomizedException;
import org.sopt.practice.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BlogService blogService;
    private final MemberService memberService;

    public String writePost(Long blogId, Long memberId, PostCreateRequest postCreateRequest) {
        // 블로그 소유권 검증
        blogService.validateBlogMember(blogId, memberId);
        // 글 생성 및 저장 로직
        Post post = postRepository.save(Post.create(
                blogService.findBlogById(blogId), // Blog 객체 검색
                postCreateRequest.title(),
                postCreateRequest.content()
        ));
        return post.toString();
    }

    public List<Post> findAllPostsByBlogId(Long blogId, Long memberId) {
        // 블로그 소유권 검증
        blogService.validateBlogMember(blogId, memberId);
        List<Post> posts = postRepository.findByBlogId(blogId);
        if (posts.isEmpty()) {
            throw new CustomizedException(ErrorMessage.POST_NOT_FOUND_BY_BLOG_ID_EXCEPTION);
        }
        return posts;
    }

    public Post findPostById(Long blogId, Long memberId, Long postId) {
        // 블로그 소유권 검증
        blogService.validateBlogMember(blogId, memberId);
        // 특정 포스트 ID로 게시물 검색
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomizedException(ErrorMessage.POST_NOT_FOUND_BY_POST_ID_EXCEPTION));
    }

}
