package com.example.ms.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @RequestMapping("users")
    fun index(): String {
        return "Greetings from Spring Boot! demo ~ demo˚"
    }
}