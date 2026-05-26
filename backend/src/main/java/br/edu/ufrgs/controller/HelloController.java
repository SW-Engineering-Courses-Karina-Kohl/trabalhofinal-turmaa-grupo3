package com.youruni.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot on Tomcat - now with a watcher! 🚀";
    }

    @GetMapping("/hello/{name}")
    public String helloWithName(@PathVariable String name) {
        return "Hello, " + name + "! Welcome to the API.";
    }
}
