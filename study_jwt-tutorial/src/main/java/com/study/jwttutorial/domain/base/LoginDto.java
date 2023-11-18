package com.study.jwttutorial.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String nm;

    @NotNull
    @Size(min = 3, max = 50)
    private String pwd;

}
