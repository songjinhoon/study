package com.study.demoinflearnrestapi.domain.member;

import com.study.demoinflearnrestapi.domain.common.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("시큐리티 인증 성공")
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
        memberService.save(member);

        //when
        UserDetailsService userDetailsService = memberService;
        UserDetails userDetails = userDetailsService.loadUserByUsername("hijinhoon");

        //then
        assertThat(userDetails.getUsername()).isEqualTo(account);
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

//    @Test(expected = UsernameNotFoundException.class) -> junit4
    @Test
    @DisplayName("시큐리티 인증 실패")
    public void findByUsernameFail() {
        String username = "randomDWadwajd8o1";
        UsernameNotFoundException assertThrows = assertThrows(UsernameNotFoundException.class, () -> memberService.loadUserByUsername(username));
        assertEquals(username, assertThrows.getMessage());
    }

}