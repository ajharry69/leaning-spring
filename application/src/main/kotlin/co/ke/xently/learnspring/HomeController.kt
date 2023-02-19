package co.ke.xently.learnspring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun get(): String {
        return """Welcome to Xently. The site is built with <a href="https://spring.io/" target="_blank">Spring Boot</a>! Through the site, you can <a href="/greeting" target='_blank'>say hello</a> to arbitrary names through get request parameters."""
    }
}