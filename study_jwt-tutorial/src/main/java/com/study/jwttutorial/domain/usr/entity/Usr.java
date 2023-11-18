package com.study.jwttutorial.domain.usr.entity;

import com.study.jwttutorial.domain.auth.entity.Auth;
import com.study.jwttutorial.domain.usr.dto.UsrDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Entity @Getter
public class Usr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @NotNull
    @Column(length = 50, unique = true) @Size(min = 3, max = 50)
    private String nm;

    @Column(length = 100) @Size(min = 3, max = 100)
    private String pwd;

    @Column(length = 50) @Size(min = 3, max = 50)
    private String nickNm;

    private Boolean useAt;

    @ManyToMany
    @JoinTable(
            name="usr_auth",
            joinColumns = {@JoinColumn(name = "usr_id", referencedColumnName = "usr_id")},
            inverseJoinColumns = {@JoinColumn(name = "auth_id", referencedColumnName = "auth_id")})
    private Set<Auth> auths;

    public static Usr create(UsrDto.Insert insert) {
        return Usr.builder()
                .nm(insert.getNm())
                .pwd(insert.getEncodingPwd())
                .nickNm(insert.getNickNm())
                .useAt(insert.getUseAt())
                .auths(insert.getAuths())
                .build();
    }

}
