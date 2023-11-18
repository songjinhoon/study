package com.study.spring.cloud.orderservice.domain.dto.kafka;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
public class KafkaOrderDto {

    private Schema schema;

    private Payload payload;

    public static KafkaOrderDto of(Schema schema, Payload payload) {
        return KafkaOrderDto.builder()
                .schema(schema)
                .payload(payload)
                .build();
    }

}
