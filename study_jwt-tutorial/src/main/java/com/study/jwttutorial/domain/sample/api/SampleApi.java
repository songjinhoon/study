package com.study.jwttutorial.domain.sample.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/sample")
@RestController
public class SampleApi {

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

}
