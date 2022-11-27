package com.study.spring.cloud.userservice.controller;

import com.study.spring.cloud.userservice.dto.SaveUserDto;
import com.study.spring.cloud.userservice.dto.UserDto;
import com.study.spring.cloud.userservice.entity.User;
import com.study.spring.cloud.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    private final Environment environment;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return environment.getProperty("greeting.message");
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody SaveUserDto saveUserDto) {
        User user = userService.saveUser(UserDto.of(saveUserDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
