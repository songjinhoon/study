package com.study.spring.cloud.userservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.spring.cloud.userservice.domain.dto.SignInDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 스프링 시큐리티의 기본 인증 url인 /login 호출시...
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Environment environment;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SignInDto signInDto = new ObjectMapper().readValue(request.getInputStream(), SignInDto.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(signInDto.getAccount(), signInDto.getPassword(), List.of()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserSecurityDto userSecurityDto = (UserSecurityDto) authResult.getPrincipal();
        String token = Jwts.builder()
                .setSubject(userSecurityDto.getUserDto().getAccount())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time")))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
        response.addHeader("token", token);
        response.addHeader("userId", userSecurityDto.getUserDto().getAccount());
    }

}
