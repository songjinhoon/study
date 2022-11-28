package com.study.spring.cloud.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.spring.cloud.userservice.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)  // 널은 응답에서 제거하겠다.
public class UserDto {

    private String userId;

    private String email;

    private String name;

    private String pwd;

    private String encryptedPwd;

    private List<Order> orders;

    public static UserDto of(SaveUserDto saveUserDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(saveUserDto, UserDto.class);
    }

    public static UserDto of(User user) {
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        userDto.setOrders(new ArrayList<>());
        return userDto;
    }

    public static List<UserDto> of(List<User> users) {
        return users.stream().map(UserDto::of).collect(Collectors.toList());
    }

}
