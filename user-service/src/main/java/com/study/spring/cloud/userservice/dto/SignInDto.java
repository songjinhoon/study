package com.study.spring.cloud.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
public class SignInDto {

    @NotEmpty
    @Size(min = 5)
    private String account;

    @NotEmpty
    @Size(min = 5)
    private String password;

}
