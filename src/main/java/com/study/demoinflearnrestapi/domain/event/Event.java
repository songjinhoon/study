package com.study.demoinflearnrestapi.domain.event;

import com.study.demoinflearnrestapi.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter // @Setter
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private Integer id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        // Update free
        this.free = this.basePrice == 0 && this.maxPrice == 0;
        // Update location
        this.offline = this.location != null && !this.location.isBlank();
    }

    public void update(EventDto eventDto) {
        this.name = eventDto.getName();
        this.description = eventDto.getDescription();
        this.beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();
        this.closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
        this.beginEventDateTime = eventDto.getBeginEventDateTime();
        this.location = eventDto.getLocation();
        this.basePrice = eventDto.getBasePrice();
        this.maxPrice = eventDto.getMaxPrice();
        this.limitOfEnrollment = eventDto.getLimitOfEnrollment();
    }
}
/*
 * @Data -> Entity에다가는 쓰면안됨 Equals and Hashcode를 구현할때 모든 필드를 다 참조하기 때문에 연관관계 상호 참조때문에 스택 오버 플로우 발생 여지가 있음
 * @Setter -> 엔티티는 의미있는 메소드를 활용하자.
 * */