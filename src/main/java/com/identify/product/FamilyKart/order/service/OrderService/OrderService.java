package com.identify.product.FamilyKart.order.service.OrderService;

import com.identify.product.FamilyKart.order.payload.OrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


public interface    OrderService {
    @Transactional
    OrderDTO placeOrder(String loggedInUserEmail, Long addressID, String paymentMethod, String paymentGatewayName, String paymentGatewayPaymentID, String paymentGatewayStatus, String paymentGatewayResponseMessage);
}
