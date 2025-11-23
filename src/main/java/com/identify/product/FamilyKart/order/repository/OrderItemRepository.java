package com.identify.product.FamilyKart.order.repository;

import com.identify.product.FamilyKart.order.model.Order;
import com.identify.product.FamilyKart.order.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
}
