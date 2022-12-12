package com.study.spring.cloud.userservice.domain.service;

import com.study.spring.cloud.userservice.client.OrderServiceClient;
import com.study.spring.cloud.userservice.domain.dto.UserDto;
import com.study.spring.cloud.userservice.domain.entity.User;
import com.study.spring.cloud.userservice.domain.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Environment environment;

    private final RestTemplate restTemplate;

    private final OrderServiceClient orderServiceClient;

    /***** DB *****/
    private List<User> findAll() {
        return userRepository.findAll();
    }

    private User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
    /***** DB *****/

    public List<UserDto> find() {
        return UserDto.of(findAll());
    }

    public UserDto find(UUID userId) {
//        ResponseEntity<List<OrderDto>> exchange = restTemplate.exchange(String.format(Objects.requireNonNull(environment.getProperty("order_service.url")), id) , HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {});
//        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(String.format(Objects.requireNonNull(environment.getProperty("order_service.url")), id), Object[].class);
//        return UserDto.of(findById(id), Arrays.asList(Objects.requireNonNull(responseEntity.getBody())));
        return UserDto.of(findById(userId), orderServiceClient.getOrders(userId));
    }

    public UserDto saveUser(UserDto userDto) {
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        return UserDto.of(userRepository.save(User.create(userDto)));
    }

}
