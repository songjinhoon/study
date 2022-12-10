package com.study.spring.cloud.userservice.domain.service;

import com.study.spring.cloud.userservice.domain.dto.OrderDto;
import com.study.spring.cloud.userservice.domain.dto.UserDto;
import com.study.spring.cloud.userservice.domain.entity.User;
import com.study.spring.cloud.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Environment environment;

    private final RestTemplate restTemplate;

    private List<User> findAll() {
        return userRepository.findAll();
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<UserDto> find() {
        return UserDto.of(findAll());
    }

    public UserDto find(Long id) {
        ResponseEntity<List<OrderDto>> exchange = restTemplate.exchange(String.format(Objects.requireNonNull(environment.getProperty("order_service.url")), id) , HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {});
        return UserDto.of(findById(id), exchange.getBody());
    }

    public UserDto saveUser(UserDto userDto) {
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        return UserDto.of(userRepository.save(User.create(userDto)));
    }

}
