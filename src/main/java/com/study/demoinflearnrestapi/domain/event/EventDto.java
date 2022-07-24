package com.study.demoinflearnrestapi.domain.event;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/*
*   프로젝트에서는 @Gette만 있어도되는데 테스트 코드를 추가하기위해 @Builder를 사용했고, @Builder를 사용하다보니 mapstruct에서 생성자를 요구하게 되어
*   생성자 어노테이션까지 추가해줌.
* */
@NoArgsConstructor @AllArgsConstructor
@Builder @Getter // @Setter
public class EventDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime beginEnrollmentDateTime;
    @NotNull
    private LocalDateTime closeEnrollmentDateTime;
    @NotNull
    private LocalDateTime beginEventDateTime;
    @NotNull
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    @Min(0)
    private int basePrice; // (optional)
    @Min(0)
    private int maxPrice; // (optional)
    @Min(0)
    private int limitOfEnrollment;

}
