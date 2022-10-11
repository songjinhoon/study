package com.study.demoinflearnrestapi.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "my-app")
public class AppProperties {

    private String adminUsername;

    private String adminPassword;

    private String adminEmail;

    private String userUsername;

    private String userPassword;

    private String userEmail;

    private String clientId;

    private String clientSecret;

}
