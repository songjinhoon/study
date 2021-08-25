package com.example.websockettest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class WebSocketSnrUpdtParam {

    private Long zoneId;
    private String scenarioState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuanceTime;

    @Builder
    public WebSocketSnrUpdtParam(Long zoneId, String scenarioState, LocalDateTime issuanceTime) {
        this.zoneId = zoneId;
        this.scenarioState = scenarioState;
        this.issuanceTime = issuanceTime;
    }
}
