package com.study.demoinflearnrestapi.domain.member;

import com.study.demoinflearnrestapi.common.BaseTest;
import com.study.demoinflearnrestapi.domain.common.Role;
import com.study.demoinflearnrestapi.domain.event.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest extends BaseTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EventRepository eventRepository;

    @BeforeEach
    public void setUp() {
        this.eventRepository.deleteAll();
        this.memberRepository.deleteAll();
    }

    @Test
    @DisplayName("시큐리티 인증 성공")
    public void findByUsername() {
        //given
        String account = "hijinhoon";
        String password = "hijinhoon";
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
        assertThrows(UsernameNotFoundException.class, () -> memberService.loadUserByUsername("randomDWadwajd8o1"));
    }

}