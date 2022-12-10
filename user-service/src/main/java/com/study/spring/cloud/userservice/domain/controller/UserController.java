package com.study.spring.cloud.userservice.domain.controller;

import com.study.spring.cloud.userservice.domain.dto.SignUpDto;
import com.study.spring.cloud.userservice.domain.dto.UserDto;
import com.study.spring.cloud.userservice.domain.entity.User;
import com.study.spring.cloud.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private final Environment environment;

    /***** CHECK *****/
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + environment.getProperty("local.server.port")
                + ", port(server.port)=" + environment.getProperty("server.port")
                + ", token secret=" + environment.getProperty("token.secret")
                + ", token expiration time=" + environment.getProperty("token.expiration_time")
        );
    }

    @GetMapping("/welcome")
    public String welcome() {
        return environment.getProperty("greeting.message");
    }
    /***** CHECK *****/

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.saveUser(UserDto.of(signUpDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDto.getUserId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * 일괄 조회
     */
    @GetMapping
    public ResponseEntity<?> find() {
        return ResponseEntity.ok().body(userService.find());
    }

    /**
     * 단일 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.find(id));
    }

}
