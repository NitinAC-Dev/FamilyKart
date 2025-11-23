package com.identify.product.FamilyKart.payment.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long paymentID;

    private String paymentMethod;

    private String paymentGatewayPaymentID;

    private String paymentGatewayStatus;


    private String paymentGateWayResponseMessage;

    private String paymentGateWayName;
}
