package org.sopt.practice.service;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.domain.Blog;
import org.sopt.practice.domain.Post;
import org.sopt.practice.dto.post.PostCreateRequest;
import org.sopt.practice.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BlogService blogService;

    public String write(Long blogId, PostCreateRequest postCreateRequest){
        Blog blog = blogService.findBlogById(blogId);
        Post post = postRepository.save(Post.create(blog, postCreateRequest.title(), postCreateRequest.content()));
        return post.toString();
    }



}
