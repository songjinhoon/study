package com.study.spring.cloud.userservice.domain.service;

import com.study.spring.cloud.userservice.domain.dto.UserDto;
import com.study.spring.cloud.userservice.domain.entity.User;
import com.study.spring.cloud.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> find() {
        return userRepository.findAll();
    }

    public User find(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public User saveUser(UserDto userDto) {
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(User.create(userDto));
    }

}
