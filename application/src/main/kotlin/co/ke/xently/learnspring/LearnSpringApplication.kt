package co.ke.xently.learnspring

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class LearnSpringApplication {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                super.addCorsMappings(registry)
                registry.addMapping("/orders").allowedOrigins("http://localhost:8080")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<LearnSpringApplication>(*args) {
        setBannerMode(Banner.Mode.CONSOLE)
    }
}
