package org.sopt.practice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.SuccessMessage;
import org.sopt.practice.common.dto.SuccessStatusResponse;
import org.sopt.practice.dto.post.PostCreateRequest;
import org.sopt.practice.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/article")
    public ResponseEntity<SuccessStatusResponse> writeArticle(@RequestHeader Long blogId,
                                                              @Valid @RequestBody PostCreateRequest postCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", postService.write(blogId, postCreateRequest))
                .body(SuccessStatusResponse.of(SuccessMessage.ARTICLE_WRITE_SUCCESS));
    }

}
