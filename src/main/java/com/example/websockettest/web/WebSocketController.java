package com.example.websockettest.web;

import com.example.websockettest.dto.WebSocketSnrUpdtParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/websocket")
    public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/update/scenario")
    public ResponseEntity<Void> updateScenario(@RequestBody WebSocketSnrUpdtParam webSocketSnrUpdtParam) {
        System.out.println(webSocketSnrUpdtParam.toString());
        simpMessagingTemplate.convertAndSend("/topic/updateScenario", webSocketSnrUpdtParam);
        return ResponseEntity.ok().build();
    }
}
