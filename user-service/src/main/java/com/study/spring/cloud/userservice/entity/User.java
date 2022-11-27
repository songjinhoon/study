package com.study.spring.cloud.userservice.entity;

import com.study.spring.cloud.userservice.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String encryptedPwd;

    public static User create(UserDto userDto) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .encryptedPwd(userDto.getEncryptedPwd())
                .build();
    }

}
