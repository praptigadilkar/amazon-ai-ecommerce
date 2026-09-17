package com.amazonai.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonai.backend.entity.Payment;
import com.amazonai.backend.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;


    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    // Create payment
    public Payment addPayment(Payment payment) {

        if (payment.getPaymentStatus() == null) {
            payment.setPaymentStatus("PENDING");
        }

        return paymentRepository.save(payment);
    }


    // Get all payments
    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }


    // Get payment by id
    public Payment getPaymentById(Long id) {

        return paymentRepository.findById(id)
                .orElse(null);
    }
}