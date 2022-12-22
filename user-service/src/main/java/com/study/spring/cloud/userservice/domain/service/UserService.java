package com.study.spring.cloud.userservice.domain.service;

import com.study.spring.cloud.userservice.client.OrderServiceClient;
import com.study.spring.cloud.userservice.domain.dto.UserDto;
import com.study.spring.cloud.userservice.domain.entity.User;
import com.study.spring.cloud.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final OrderServiceClient orderServiceClient;

    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    /***** DB *****/
    private List<User> findAll() {
        return userRepository.findAll();
    }

    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    /***** DB *****/

    public List<UserDto> find() {
        return UserDto.of(findAll());
    }

    public UserDto find(String userId) {
//        ResponseEntity<List<OrderDto>> exchange = restTemplate.exchange(String.format(Objects.requireNonNull(environment.getProperty("order_service.url")), id) , HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {});
//        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(String.format(Objects.requireNonNull(environment.getProperty("order_service.url")), id), Object[].class);
//        return UserDto.of(findById(id), Arrays.asList(Objects.requireNonNull(responseEntity.getBody())));
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        List<?> orderDtos = circuitbreaker.run(
                () -> orderServiceClient.getOrders(userId),
                throwable -> List.of()
        );

        return UserDto.of(findById(userId), orderDtos);
    }

    public UserDto saveUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        return UserDto.of(userRepository.save(User.create(userDto)));
    }

}
