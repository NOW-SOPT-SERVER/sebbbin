package org.sopt.practice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.domain.Blog;
import org.sopt.practice.domain.Member;
import org.sopt.practice.dto.blog.BlogCreateRequest;
import org.sopt.practice.dto.blog.BlogTitleUpdateRequest;
import org.sopt.practice.exception.CustomizedException;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.repository.BlogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberService memberService;

    public String create(Long memberId, BlogCreateRequest blogCreateRequest) {
        //member찾기
        Member member = memberService.findById(memberId);
        Blog blog = blogRepository.save(Blog.create(member, blogCreateRequest.title(), blogCreateRequest.description()));
        return blog.toString();
    }

    @Transactional
    public void updateTitle(Long blogId, BlogTitleUpdateRequest blogTitleUpdateRequest) {
        Blog blog = findBlogById(blogId);
        blog.updateTitle(blogTitleUpdateRequest.title());
    }


    public Blog findBlogById(Long blogId){
        return blogRepository.findById(blogId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BLOG_NOT_FOUND)
        );
    }
    // 멤버의 블로그 소유권을 검증하는 메서드
    public void validateBlogMember(Long blogId, Long memberId) {
        Blog blog = this.findBlogById(blogId);
        if (!blog.getMember().getId().equals(memberId)) {
            throw new CustomizedException(ErrorMessage.UNAUTHORIZED_ACCESS);
        }
    }
}