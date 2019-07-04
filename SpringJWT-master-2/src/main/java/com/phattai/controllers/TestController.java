package com.phattai.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class TestController {
    @RequestMapping(method = RequestMethod.GET)
    public String news() {
        return "user";
    }
}
