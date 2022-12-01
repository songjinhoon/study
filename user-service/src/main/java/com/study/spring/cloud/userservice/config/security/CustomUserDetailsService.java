package com.study.spring.cloud.userservice.config.security;

import com.study.spring.cloud.userservice.domain.dto.UserDto;
import com.study.spring.cloud.userservice.domain.entity.User;
import com.study.spring.cloud.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * UserService 내부에 직접 구현해서 사용해도되나, 인증과관련된 부분은 분리를하고싶음
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByAccount(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserSecurityDto(user.getAccount(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>(), UserDto.of(user));
    }

}
