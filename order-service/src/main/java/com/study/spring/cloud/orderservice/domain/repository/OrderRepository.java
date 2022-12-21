package com.study.spring.cloud.orderservice.domain.repository;

import com.study.spring.cloud.orderservice.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserId(String userId);

}
