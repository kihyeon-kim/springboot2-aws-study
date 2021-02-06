package com.example.sb2aws.study.web.service

import com.example.sb2aws.study.web.domain.post.PostRepository
import com.example.sb2aws.study.web.dto.PostResponseDto
import com.example.sb2aws.study.web.dto.PostSaveRequestDto
import com.example.sb2aws.study.web.dto.PostUpdateRequestDto
import com.example.sb2aws.study.web.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
) {
    @Transactional
    fun save(requestDto: PostSaveRequestDto): Long =
        postRepository.save(requestDto.toEntity()).id!!

    @Transactional
    fun update(
        id: Long,
        requestDto: PostUpdateRequestDto,
    ): Long =
        postRepository.findByIdOrNull(id)?.let { post ->
            with(requestDto) { post.update(title, content) }
            id
        } ?: throw IllegalArgumentException("Not Found. id: $id")

    fun getById(id: Long): PostResponseDto =
        postRepository.findByIdOrNull(id)?.let { post ->
            PostResponseDto.of(post)
        } ?: throw IllegalArgumentException("Not Found. id: $id")
}