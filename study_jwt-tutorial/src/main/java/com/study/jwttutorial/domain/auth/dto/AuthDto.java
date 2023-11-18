package com.study.jwttutorial.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class AuthDto {

    @Size(min = 1, max = 50)
    private String id;

    @Getter
    public static class Insert extends AuthDto {

        @Builder
        public Insert(String id) {
            super.id = id;
        }

    }

}
