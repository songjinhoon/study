package com.study.spring.cloud.secondservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/second-service")
@RestController
public class SecondServiceController {

    private final Environment environment;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Second service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info(header);
        return "Hello world in second service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("===> Server port={}", request.getServerPort());

        return String.format("Hi, there. This is a message from Second service on Port %s", environment.getProperty("local.server.port"));
    }

}
