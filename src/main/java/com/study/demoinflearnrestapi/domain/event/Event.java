package com.study.demoinflearnrestapi.domain.event;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Event {

    private Integer id;
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
    private boolean offline;
    private boolean free;
    private EventStatus eventStatus;

}
/*
* @Data -> Entity에다가는 쓰면안됨 Equals and Hashcode를 구현할때 모든 필드를 다 참조하기 때문에 연관관계 상호 참조때문에 스택 오버 플로우 발생 여지가 있음
* */