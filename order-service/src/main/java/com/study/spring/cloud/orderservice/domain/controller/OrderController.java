package com.study.spring.cloud.orderservice.domain.controller;

import com.study.spring.cloud.orderservice.domain.dto.OrderDto;
import com.study.spring.cloud.orderservice.domain.dto.OrderSaveDto;
import com.study.spring.cloud.orderservice.domain.entity.Order;
import com.study.spring.cloud.orderservice.domain.service.OrderService;
import com.study.spring.cloud.orderservice.messagequeue.KafkaProducer;
import com.study.spring.cloud.orderservice.messagequeue.OrderProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/order-service")
@RestController
public class OrderController {

    private final OrderService orderService;

    private final OrderProducer orderProducer;

    private final KafkaProducer kafkaProducer;

    private final Environment environment;

    /**** CHECK *****/
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", environment.getProperty("local.server.port"));
    }
    /***** CHECK *****/

    /**
     * 조회 - 유저아이디
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable String userId) throws Exception {
        log.info("Before call order-service");
        
        List<OrderDto> orderDtos = OrderDto.of(orderService.findByUserId(userId));

        try {
            Thread.sleep(1000);
            throw new Exception("장애 발생");
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        log.info("After call order-service");
        return ResponseEntity.ok().body(orderDtos);
    }

    /**
     * 조회 - 상세
     */
    @GetMapping("/{id}/detail")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(OrderDto.of(orderService.findById(id)));
    }

    /**
     * 저장
     */
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid OrderSaveDto orderSaveDto) {
        log.info("Before add order-service");

        OrderDto orderDto = OrderDto.of(orderSaveDto);

        Order order = orderService.save(orderDto);
        /*
        kafkaProducer.send("example-catalog-topic", orderDto); // 카탈로그 서비스와 연동
        orderProducer.send("tn_order", orderDto); // 싱크커넥터를 이용해 tn_order 토픽을 바라보는 싱크커넥터를 이용하여 tn_order 데이터베이스에 데이터 저장
        */

        log.info("After add order-service");
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto.of(order));
    }

}

