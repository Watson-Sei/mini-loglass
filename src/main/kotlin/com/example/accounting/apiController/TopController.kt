package com.example.accounting.apiController

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("apiTopController")
@RequestMapping("/api")
class TopController {

    @GetMapping("/")
    fun top(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }
}
