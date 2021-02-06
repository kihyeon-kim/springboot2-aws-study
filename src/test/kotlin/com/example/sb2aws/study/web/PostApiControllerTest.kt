package com.example.sb2aws.study.web

import com.example.sb2aws.study.web.domain.post.Post
import com.example.sb2aws.study.web.domain.post.PostRepository
import com.example.sb2aws.study.web.dto.PostSaveRequestDto
import com.example.sb2aws.study.web.dto.PostUpdateRequestDto
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod.PUT
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
internal class PostApiControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var postRepository: PostRepository

    @AfterEach
    fun tearDown() {
        postRepository.deleteAll()
    }

    @Test
    fun `POST 등록`() {
        // given
        val title = "title"
        val content = "content"
        val requestPostDto = PostSaveRequestDto(
            title = title,
            content = content,
            author = "author"
        )

        val url = "http://localhost:$port/api/v1/posts"

        // when, then
        restTemplate.postForEntity<Long>(url, requestPostDto).let {
            then(it).satisfies { response ->
                then(response.statusCode).isEqualTo(OK)
                then(response.body).isGreaterThan(0L)
            }
        }

        postRepository.findAll().let { posts ->
            then(posts.first()).satisfies { post ->
                then(post.title).isEqualTo(title)
                then(post.content).isEqualTo(content)
            }
        }
    }

    @Test
    fun `POST 수정`() {
        // given
        val savedPost = postRepository.save(
            Post(
                title = "title",
                content = "content",
                author = "author"
            )
        )

        val updateId = savedPost.id
        val expectedTitle = "title2"
        val expectedContent = "content2"

        val requestDto = PostUpdateRequestDto(
            title = expectedTitle,
            content = expectedContent
        )

        val url = "http://localhost:$port/api/v1/posts/$updateId"

        // when, then
        restTemplate.exchange<Long>(url, PUT, HttpEntity(requestDto)).let { responseEntity ->
            then(responseEntity).satisfies {
                then(it.statusCode).isEqualTo(OK)
                then(it.body).isGreaterThan(0L)
            }
        }

        postRepository.findAll().let { posts ->
            then(posts.first()).satisfies { post ->
                then(post.title).isEqualTo(expectedTitle)
                then(post.content).isEqualTo(expectedContent)
            }
        }
    }
}