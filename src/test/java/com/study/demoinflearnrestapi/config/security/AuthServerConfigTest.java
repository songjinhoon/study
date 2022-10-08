package com.study.demoinflearnrestapi.config.security;

import com.study.demoinflearnrestapi.common.BaseControllerTest;
import com.study.demoinflearnrestapi.domain.common.Role;
import com.study.demoinflearnrestapi.domain.member.Member;
import com.study.demoinflearnrestapi.domain.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        //given
        String clientId = "myApp";
        String clientSecret = "pass";
        String username = "hijinhoon";
        String password = "hijinhoon123";
        Member member = Member.builder()
                .account(username)
                .password(password)
                .email("hijinhoon@naver.com")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .build();
        memberService.save(member);

        //when - then
        mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(clientId, clientSecret))
                        .param("username", username)
                        .param("password", password)
                        .param("grant_type", "password")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
        ;
    }

}