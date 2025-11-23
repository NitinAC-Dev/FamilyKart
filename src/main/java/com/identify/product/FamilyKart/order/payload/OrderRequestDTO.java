package com.identify.product.FamilyKart.order.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private Long addressID;

    private String paymentMethod;

    private String paymentGatewayName;

    private String paymentGatewayPaymentID;

    private String paymentGatewayStatus;

    private String paymentGatewayResponseMessage;


}
