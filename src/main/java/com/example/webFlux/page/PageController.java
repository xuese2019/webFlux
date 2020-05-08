package com.example.webFlux.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/page/{a}/{b}")
    public String page(@PathVariable("a") String a, @PathVariable("b") String b) {
        return "/" + a + "/" + b;
    }
}
