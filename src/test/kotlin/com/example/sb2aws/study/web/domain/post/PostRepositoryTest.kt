package com.example.sb2aws.study.web.domain.post

import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class PostRepositoryTest {
    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    fun `게시글 저장 불러오기`() {
        // given
        val title = "테스트 게시글"
        val content = "테스트 본문"

        postRepository.save(
            Post(title = title, content = content, author = "KiHyeon, Kim")
        )

        // when
        val posts = postRepository.findAll()

        // then
        val post = posts.first()

        // assertions
        assertAll(
            { assertEquals(post.title, title) },
            { assertEquals(post.content, content) }
        )

        // assertj
        then(post).satisfies {
            then(it.title).isEqualTo(title)
            then(it.content).isEqualTo(content)
        }
    }

    @Test
    fun `BaseTimeEntity 등록`() {
        // given
        val yesterday = LocalDateTime.now().minusDays(1L)

        postRepository.save(
            Post(title = "title", content = "content", author = "author")
        )

        postRepository.findAll().let { posts ->
            then(posts.first()).satisfies { post ->
                then(post.createdDate).isAfter(yesterday)
                then(post.modifiedDate).isAfter(yesterday)
            }
        }
    }
}