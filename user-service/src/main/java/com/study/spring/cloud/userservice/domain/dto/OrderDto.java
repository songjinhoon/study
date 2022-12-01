package com.study.spring.cloud.userservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDto {

    private String id;

    private Integer qty;

    private Integer unitPrice;

    private Integer totalPrice;

    private String productId;

}
