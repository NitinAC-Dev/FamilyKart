package com.identify.product.FamilyKart.order.controller;


import com.identify.product.FamilyKart.order.payload.OrderDTO;
import com.identify.product.FamilyKart.order.payload.OrderRequestDTO;
import com.identify.product.FamilyKart.order.service.OrderService.OrderService;
import com.identify.product.FamilyKart.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
private OrderService orderService;

    @Autowired
    private AuthUtils authUtils;


    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable String paymentMethod,
                                                @RequestBody OrderRequestDTO orderRequestDTO) {
        // Implementation for creating an order with the specified payment method

        String loggedInUserEmail = authUtils.getLoggedInUserEmail();
       OrderDTO orderDTO= orderService.placeOrder(
                loggedInUserEmail,
                orderRequestDTO.getAddressID(),
                paymentMethod,
                orderRequestDTO.getPaymentGatewayName(),
                orderRequestDTO.getPaymentGatewayPaymentID(),
                orderRequestDTO.getPaymentGatewayStatus(),
                orderRequestDTO.getPaymentGatewayResponseMessage()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }
}
