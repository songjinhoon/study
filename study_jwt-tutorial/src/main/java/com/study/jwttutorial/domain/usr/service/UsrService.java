package com.study.jwttutorial.domain.usr.service;

import com.study.jwttutorial.domain.auth.dto.AuthDto;
import com.study.jwttutorial.domain.auth.entity.Auth;
import com.study.jwttutorial.domain.usr.dto.UsrDto;
import com.study.jwttutorial.domain.usr.entity.Usr;
import com.study.jwttutorial.domain.usr.repository.UsrRepository;
import com.study.jwttutorial.util.SecurityUtil;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UsrService {

    private final UsrRepository usrRepository;

    private final PasswordEncoder passwordEncoder;

    public Usr signup(UsrDto.Insert insert) {
        if (usrRepository.findOneWithAuthsByNm(insert.getNm()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어있는 유저입니다.");
        }

        Auth auth = Auth.create(AuthDto.Insert.builder()
                .id("ROLE_USER")
                .build());
        insert.setEncodingPwd(passwordEncoder.encode(insert.getPwd()));
        insert.setAuths(Collections.singleton(auth));
        Usr usr = Usr.create(insert);

        return usrRepository.save(usr);
    }

    public Optional<Usr> getUserWithAuthorities(String nm) {
        return usrRepository.findOneWithAuthsByNm(nm);
    }

    /*
    * * 현재 시큐리티 컨텍스트에 저장되어있는 nm에 해당하는 데이터 조회
    * */
    public Optional<Usr> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(usrRepository::findOneWithAuthsByNm);
    }


/*    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }*/

}
