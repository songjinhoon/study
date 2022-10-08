package com.study.demoinflearnrestapi.config;

import com.study.demoinflearnrestapi.domain.common.Role;
import com.study.demoinflearnrestapi.domain.member.Member;
import com.study.demoinflearnrestapi.domain.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Profile("default")
    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            MemberService memberService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Member member = Member.builder()
                        .account("hijinhoon")
                        .password("hijinhoon123")
                        .email("hijinhoon@naver.com")
                        .roles(Set.of(Role.ADMIN, Role.USER))
                        .build();
                memberService.save(member);
            }
        };

    }

}
