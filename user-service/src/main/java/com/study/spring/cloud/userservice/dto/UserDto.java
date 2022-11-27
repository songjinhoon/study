package com.study.spring.cloud.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    private String userId;

    private String email;

    private String name;

    private String pwd;

    private LocalDateTime rgsDt;

    private String encryptedPwd;

    public static UserDto of(SaveUserDto saveUserDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(saveUserDto, UserDto.class);
    }

}
