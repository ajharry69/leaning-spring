package co.ke.xently.learnspring.features.greetings

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GreetingController {
    @GetMapping("/greeting")
    fun get(@RequestParam(defaultValue = "World", required = false) name: String, model: Model): String {
        model["name"] = name
        return "greeting"
    }
}