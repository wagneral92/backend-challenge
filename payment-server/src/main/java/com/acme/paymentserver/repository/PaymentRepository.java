package com.acme.paymentserver.repository;

import com.acme.paymentserver.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
