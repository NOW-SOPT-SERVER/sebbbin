package org.sopt.practice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.SuccessMessage;
import org.sopt.practice.common.dto.SuccessStatusResponse;
import org.sopt.practice.dto.blog.BlogCreateRequest;
import org.sopt.practice.dto.blog.BlogTitleUpdateRequest;
import org.sopt.practice.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    //성공했을 때 값을 반환하기 위한 SuccessStatusResponse
    @PostMapping("/blog")
    public ResponseEntity<SuccessStatusResponse> creatBlog(@RequestHeader Long memberId,
                                                           @RequestBody BlogCreateRequest blogCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", blogService.create(memberId, blogCreateRequest))
                .body(SuccessStatusResponse.of(SuccessMessage.BLOG_CREATE_SUCCESS));
    }

    @PatchMapping("/blog/{blogId}/title")
    public ResponseEntity updateBlogTitle(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogTitleUpdateRequest blogTitleUpdateRequest
    ) {
        blogService.updateTitle(blogId, blogTitleUpdateRequest);
        return ResponseEntity.noContent().build();
    }


}
