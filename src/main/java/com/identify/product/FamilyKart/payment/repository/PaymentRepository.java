package com.identify.product.FamilyKart.payment.repository;

import com.identify.product.FamilyKart.order.model.Order;
import com.identify.product.FamilyKart.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
