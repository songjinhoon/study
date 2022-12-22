package com.study.spring.cloud.userservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private String orderId;

    private String userId;

    private String catalogId;

    private Integer quantity;

    private Integer unitPrice;

    private Integer totalPrice;

}
