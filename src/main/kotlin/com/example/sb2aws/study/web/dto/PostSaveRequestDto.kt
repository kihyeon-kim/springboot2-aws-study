package com.example.sb2aws.study.web.dto

import com.example.sb2aws.study.web.domain.post.Post

data class PostSaveRequestDto(
    val title: String,
    val content: String,
    val author: String,
)

fun PostSaveRequestDto.toEntity(): Post =
    Post(
        title = title,
        content = content,
        author = author
    )
