package com.study.spring.cloud.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveUserDto {

    @NotEmpty
    @Size(min = 2)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotEmpty
    @Size(min = 8)
    private String pwd;

}
