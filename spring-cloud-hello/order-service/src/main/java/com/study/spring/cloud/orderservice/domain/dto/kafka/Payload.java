package com.study.spring.cloud.orderservice.domain.dto.kafka;

import com.study.spring.cloud.orderservice.domain.dto.OrderDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class Payload {

    private String order_id;

    private String user_id;

    private Long catalog_id;

    private Integer quantity;

    private Integer unit_price;

    private Integer total_price;

    public static Payload of(OrderDto orderDto) {
        return Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .catalog_id(orderDto.getCatalogId())
                .quantity(orderDto.getQuantity())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();
    }

}
