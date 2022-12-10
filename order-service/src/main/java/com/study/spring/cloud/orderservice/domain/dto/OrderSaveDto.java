package com.study.spring.cloud.orderservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class OrderSaveDto {

    private UUID userId;

    private String catalogId;

    private Integer quantity;

    private Integer unitPrice;

}
