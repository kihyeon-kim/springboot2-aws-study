package com.example.sb2aws.study.web

import com.example.sb2aws.study.web.dto.HelloResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun greeting() = "hello"

    @GetMapping("/hello/dto")
    fun getHelloDto(
        @RequestParam("name") name: String,
        @RequestParam("amount") amount: Int,
    ): HelloResponseDto = HelloResponseDto(name, amount)
}