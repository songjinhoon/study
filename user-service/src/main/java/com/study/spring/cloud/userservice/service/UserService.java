package com.study.spring.cloud.userservice.service;

import com.study.spring.cloud.userservice.dto.UserDto;
import com.study.spring.cloud.userservice.entity.User;
import com.study.spring.cloud.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto) {
        userDto.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        return userRepository.save(User.create(userDto));
    }

}
