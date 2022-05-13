package com.study.jwttutorial.domain.usr.dto;

import com.study.jwttutorial.domain.auth.entity.Auth;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter @Setter
public class UsrDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String nm;

//    @JsonProperty(acces = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String pwd;

    @NotNull
    @Size(min = 3, max = 100)
    private String nickNm;
    
    private Boolean useAt;

    @NoArgsConstructor
    @Getter @Setter
    public static class Insert extends UsrDto {

        @Size(min = 3, max = 100)
        private String encodingPwd;

        private Set<Auth> auths;

        @Builder
        public Insert(String nm, String pwd, String nickNm, Boolean useAt) {
            super.nm = nm;
            super.pwd = pwd;
            super.nickNm = nickNm;
            super.useAt = useAt;
        }
        
        
    }

}
