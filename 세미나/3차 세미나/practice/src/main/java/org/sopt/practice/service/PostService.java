package org.sopt.practice.service;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.ErrorResponse;
import org.sopt.practice.domain.Blog;
import org.sopt.practice.domain.Member;
import org.sopt.practice.domain.Post;
import org.sopt.practice.dto.post.PostCreateRequest;
import org.sopt.practice.exception.UnauthorizedAccessException;
import org.sopt.practice.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BlogService blogService;
    private final MemberService memberService;

    public String write(Long blogId, Long memberId, PostCreateRequest postCreateRequest){
        Blog blog = blogService.findBlogById(blogId);
        Member member = memberService.findById(memberId);
        if (!blog.getMember().getId().equals(memberId)) {
            throw new UnauthorizedAccessException("Unauthorized: Member does not own the blog.");
        }
        Post post = postRepository.save(Post.create(blog, member, postCreateRequest.title(), postCreateRequest.content()));
        return post.toString();
    }

}
