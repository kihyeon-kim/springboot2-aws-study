package com.example.sb2aws.study.web

import com.example.sb2aws.study.web.dto.PostResponseDto
import com.example.sb2aws.study.web.dto.PostSaveRequestDto
import com.example.sb2aws.study.web.dto.PostUpdateRequestDto
import com.example.sb2aws.study.web.service.PostService
import org.springframework.web.bind.annotation.*

@RestController
class PostApiController(
    private val postService: PostService,
) {
    @PostMapping("/api/v1/posts")
    fun save(
        @RequestBody requestDto: PostSaveRequestDto,
    ): Long = postService.save(requestDto)

    @PutMapping("/api/v1/posts/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody requestDto: PostUpdateRequestDto,
    ): Long = postService.update(id, requestDto)

    @GetMapping("/api/v1/posts/{id}")
    fun getById(
        @PathVariable id: Long,
    ): PostResponseDto = postService.getById(id)
}