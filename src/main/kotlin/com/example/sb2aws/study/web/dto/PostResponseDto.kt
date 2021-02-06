package com.example.sb2aws.study.web.dto

import com.example.sb2aws.study.web.domain.post.Post

data class PostResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
) {
    companion object {
        fun of(post: Post): PostResponseDto = with(post) {
            PostResponseDto(
                id = id!!,
                title = title,
                content = content,
                author = author
            )
        }
    }
}
