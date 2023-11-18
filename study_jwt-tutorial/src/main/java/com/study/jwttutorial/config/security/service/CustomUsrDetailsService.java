package com.study.jwttutorial.config.security.service;

import com.study.jwttutorial.domain.usr.entity.Usr;
import com.study.jwttutorial.domain.usr.repository.UsrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomUsrDetailsService implements UserDetailsService {

    private final UsrRepository usrRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usrRepository.findOneWithAuthsByNm(username)
                .map(usr -> createUser(username, usr))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createUser(String nm, Usr usr) {
        if (!usr.getUseAt()) {
            throw new RuntimeException(nm + " -> 활성화되어 있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = usr.getAuths().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getId()))
                .collect(Collectors.toList());
        return new User(usr.getNm(), usr.getPwd(), grantedAuthorities);
    }

}
