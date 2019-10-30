package com.example.ms.controller

import org.springframework.web.bind.annotation.*

/**
 * テスト
 */
@RestController
class TestController {
    @GetMapping(path = ["/"])
    fun test(): String {
        return "ok"
    }
}