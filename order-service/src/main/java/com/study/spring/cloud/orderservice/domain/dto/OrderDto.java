package com.study.spring.cloud.orderservice.domain.dto;

import com.study.spring.cloud.orderservice.domain.entity.Order;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDto {

    private UUID orderId;

    private UUID userId;

    private String catalogId;

    private Integer quantity;

    private Integer unitPrice;

    private Integer totalPrice;

    public static OrderDto of(Order order) {
        return new ModelMapper().map(order, OrderDto.class);
    }

    public static List<OrderDto> of(List<Order> orders) {
        return orders.stream().map(OrderDto::of).collect(Collectors.toList());
    }

    public static OrderDto of(OrderSaveDto orderSaveDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = modelMapper.map(orderSaveDto, OrderDto.class);
        orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQuantity());
        return orderDto;
    }

}
