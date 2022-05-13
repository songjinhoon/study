package com.study.jwttutorial.domain.base;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class TokenDto {

    private String token;

}
