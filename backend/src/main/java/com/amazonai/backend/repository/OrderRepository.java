package com.amazonai.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazonai.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

}