package com.design.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloWorldController {

    @GetMapping("{name}")
    public String getHello(@PathVariable("name") String name) {
        return "Hello " + name + "!";
    }
}
