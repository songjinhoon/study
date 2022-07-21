package com.study.demoinflearnrestapi.domain.event;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor // 빌더 패턴과 생성자 패턴을 동시에 활용할 수 있지 -> 사실 빌더패턴만으로도 가능한데 mapstruct때문에  생성자패턴도 필요함
@Builder @Getter // @Setter
public class EventDto {

    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;

}
