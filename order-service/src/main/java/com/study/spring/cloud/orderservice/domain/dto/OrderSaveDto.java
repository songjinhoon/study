package com.study.spring.cloud.orderservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSaveDto {

    private Long userId;

    private String catalogId;

    private Integer quantity;

    private Integer unitPrice;

}
