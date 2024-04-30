package org.sopt.practice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.SuccessMessage;
import org.sopt.practice.common.dto.SuccessStatusResponse;
import org.sopt.practice.domain.Post;
import org.sopt.practice.dto.post.PostCreateRequest;
import org.sopt.practice.dto.post.PostResponse;
import org.sopt.practice.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<SuccessStatusResponse> writePost(@RequestHeader Long blogId, @RequestHeader Long memberId,
                                                           @Valid @RequestBody PostCreateRequest postCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", postService.writePost(blogId, memberId, postCreateRequest))
                .body(SuccessStatusResponse.of(SuccessMessage.POST_CREATE_SUCCESS));
    }

    @GetMapping("/post")
    public ResponseEntity<SuccessStatusResponse> findAllPostsByBlogId(@RequestHeader Long blogId, @RequestHeader Long memberId) {
        List<Post> posts = postService.findAllPostsByBlogId(blogId, memberId);
        List<PostResponse> responses = posts.stream()
                .map(post -> new PostResponse(post.getId(), post.getTitle(), post.getContent()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(SuccessStatusResponse.of(
                SuccessMessage.POST_ALL_FIND_SUCCESS.getStatus(),
                SuccessMessage.POST_ALL_FIND_SUCCESS.getMessage(),
                responses
        ));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<SuccessStatusResponse> getPostById(@RequestHeader Long blogId, @RequestHeader Long memberId,
                                                             @PathVariable Long postId) {
        Post post = postService.findPostById(blogId, memberId, postId);
        PostResponse postResponse = new PostResponse(post.getId(), post.getTitle(), post.getContent());
        return ResponseEntity.ok(new SuccessStatusResponse(
                SuccessMessage.POST_FIND_SUCCESS.getStatus(),
                SuccessMessage.POST_FIND_SUCCESS.getMessage(),
                postResponse
        ));
    }
}
