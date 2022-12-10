package com.study.spring.cloud.orderservice.domain.service;

import com.study.spring.cloud.orderservice.domain.dto.OrderDto;
import com.study.spring.cloud.orderservice.domain.entity.Order;
import com.study.spring.cloud.orderservice.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Order> findByUserId(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order save(OrderDto orderDto) {
        return orderRepository.save(Order.create(orderDto));
    }

}
