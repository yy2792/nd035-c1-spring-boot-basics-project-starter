package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IndexController implements ErrorController {
    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}