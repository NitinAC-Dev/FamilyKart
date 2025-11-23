package com.identify.product.FamilyKart.order.payload;

import com.identify.product.FamilyKart.payment.payload.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {


    private long orderID;

    private String email;

    private List<OrderItemDTO> orderItems;

    private PaymentDTO payment;

    private LocalDate orderDate;

    private String orderStatus;

    private double orderTotal;

    private Long addressID;
}
