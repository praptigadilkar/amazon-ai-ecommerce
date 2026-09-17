package com.amazonai.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazonai.backend.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}