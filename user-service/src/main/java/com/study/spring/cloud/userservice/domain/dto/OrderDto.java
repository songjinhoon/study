package com.study.spring.cloud.userservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderDto {

    private UUID orderId;

    private UUID userId;

    private String catalogId;

    private Integer quantity;

    private Integer unitPrice;

    private Integer totalPrice;

}
