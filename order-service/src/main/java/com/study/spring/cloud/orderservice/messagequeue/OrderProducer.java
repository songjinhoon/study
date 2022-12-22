package com.study.spring.cloud.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.spring.cloud.orderservice.domain.dto.OrderDto;
import com.study.spring.cloud.orderservice.domain.dto.kafka.Field;
import com.study.spring.cloud.orderservice.domain.dto.kafka.KafkaOrderDto;
import com.study.spring.cloud.orderservice.domain.dto.kafka.Payload;
import com.study.spring.cloud.orderservice.domain.dto.kafka.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = List.of(
            new Field("string", false, "order_id"),
            new Field("string", false, "user_id"),
            new Field("int64", false, "catalog_id"),
            new Field("int32", true, "quantity"),
            new Field("int32", true, "unit_price"),
            new Field("int32", true, "total_price")
    );
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("tn_order")
            .build();

    public OrderDto send(String topic, OrderDto orderDto) {
        KafkaOrderDto kafkaOrderDto = KafkaOrderDto.of(
                schema,
                Payload.of(orderDto)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = objectMapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Order Producer send data from order-service => " + kafkaOrderDto.toString());
        return orderDto;
    }

}
