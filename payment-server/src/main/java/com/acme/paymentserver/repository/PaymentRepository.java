package com.acme.paymentserver.repository;

import com.acme.paymentserver.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderId(final Long id);
}
