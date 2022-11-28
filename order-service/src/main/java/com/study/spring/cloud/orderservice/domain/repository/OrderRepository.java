package com.study.spring.cloud.orderservice.domain.repository;

import com.study.spring.cloud.orderservice.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

}
