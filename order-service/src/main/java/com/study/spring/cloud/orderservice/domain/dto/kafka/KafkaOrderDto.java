package com.study.spring.cloud.orderservice.domain.dto.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
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
