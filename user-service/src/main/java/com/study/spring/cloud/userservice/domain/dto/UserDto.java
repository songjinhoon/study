package com.study.spring.cloud.userservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.spring.cloud.userservice.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)  // 널은 응답에서 제거하겠다.
public class UserDto {

    private UUID userId;

    private String account;

    private String email;

    private String name;

    private String password;

    private String encryptedPassword;

    private List<?> orders;

    public static UserDto of(Object object) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(object, UserDto.class);
    }

    public static UserDto of(User user, List<?> orders) {
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        userDto.setOrders(orders);
        return userDto;
    }

    public static List<UserDto> of(List<User> users) {
        return users.stream().map(UserDto::of).collect(Collectors.toList());
    }

}
