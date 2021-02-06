package com.example.sb2aws.study.web

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
internal class HelloControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun hello() {
        mockMvc
            .get("/hello")
            .andExpect {
                status { isOk() }
                content { string("hello") }
            }
    }

    @Test
    fun helloDto() {
        // given
        val name = "hello"
        val amount = 100

        mockMvc
            .get("/hello/dto") {
                param("name", name)
                param("amount", amount.toString())
                accept = APPLICATION_JSON
            }
            .andExpect {
                status { isOk() }
                content { contentType(APPLICATION_JSON) }
                content {
                    jsonPath("$.name") {
                        isString()
                        value(name)
                    }
                    jsonPath("$.amount") {
                        isNumber()
                        value(amount)
                    }
                }
            }

    }
}