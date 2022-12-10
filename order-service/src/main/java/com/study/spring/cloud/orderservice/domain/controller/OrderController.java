package com.study.spring.cloud.orderservice.domain.controller;

import com.study.spring.cloud.orderservice.domain.dto.OrderDto;
import com.study.spring.cloud.orderservice.domain.dto.OrderSaveDto;
import com.study.spring.cloud.orderservice.domain.entity.Order;
import com.study.spring.cloud.orderservice.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "/order-service")
@RestController
public class OrderController {

    private final OrderService orderService;

    private final Environment environment;

    /**** CHECK *****/
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", environment.getProperty("local.server.port"));
    }
    /***** CHECK *****/

    /**
     * 단일 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(OrderDto.of(orderService.findById(id)));
    }

    /**
     * 일괄 조회 - 유저아이디
     * */
    @GetMapping("/{userId}/order")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(OrderDto.of(orderService.findByUserId(userId)));
    }

    /**
     * 저장
     * */
    @PostMapping("/order")
    public ResponseEntity<?> save(@RequestBody @Valid OrderSaveDto orderSaveDto) {
        Order save = orderService.save(OrderDto.of(orderSaveDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getOrderId())
                .toUri();
        return ResponseEntity.created(uri).body(OrderDto.of(save));
    }

}
