package com.study.demoinflearnrestapi.domain.member;


import com.study.demoinflearnrestapi.domain.common.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Integer id;

    private String account;

    private String password;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
