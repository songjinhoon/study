package com.study.jwttutorial.domain.auth.entity;

import com.study.jwttutorial.domain.auth.dto.AuthDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Entity @Getter
public class Auth {

    @Id
    @Column(name = "auth_id", length = 50) @Size(min = 1, max = 50)
    private String id;

    public static Auth create(AuthDto.Insert insert) {
        return Auth.builder()
                .id(insert.getId())
                .build();
    }

}
