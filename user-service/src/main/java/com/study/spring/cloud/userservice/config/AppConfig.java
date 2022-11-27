package com.study.spring.cloud.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

/*    @Bean
    public PasswordEncoder passwordEncoder2() {
        String encodingId = "SHA-256";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }*/

}
