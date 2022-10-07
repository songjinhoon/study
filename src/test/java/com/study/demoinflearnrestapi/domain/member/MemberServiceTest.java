package com.study.demoinflearnrestapi.domain.member;

import com.study.demoinflearnrestapi.domain.common.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("")
    public void findByUsername() {
        //given
        String account = "hijinhoon";
        String password = "hijinhoon123";
        String email = "hijinhoon@naver.com";

        Member member = Member.builder()
                .account(account)
                .password(password)
                .email(email)
                .roles(Set.of(Role.ADMIN, Role.USER))
                .build();
        memberRepository.save(member);

        //when
        UserDetailsService userDetailsService = memberService;
        UserDetails userDetails = userDetailsService.loadUserByUsername("hijinhoon");

        //then
        assertThat(userDetails.getUsername()).isEqualTo(account);
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }

}