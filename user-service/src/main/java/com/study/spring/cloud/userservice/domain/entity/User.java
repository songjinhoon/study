package com.study.spring.cloud.userservice.domain.entity;

import com.study.spring.cloud.userservice.domain.dto.UserDto;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tn_user")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(unique = true)
    private String account;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String encryptedPassword;

    public static User create(UserDto userDto) {
        return User.builder()
//                .userId(UUID.randomUUID().toString())
                .account(userDto.getAccount())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .encryptedPassword(userDto.getEncryptedPassword())
                .build();
    }

}
