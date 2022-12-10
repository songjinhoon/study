package com.study.spring.cloud.userservice.domain.entity;

import com.study.spring.cloud.userservice.domain.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tn_user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

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
                .account(userDto.getAccount())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .encryptedPassword(userDto.getEncryptedPassword())
                .build();
    }

}
