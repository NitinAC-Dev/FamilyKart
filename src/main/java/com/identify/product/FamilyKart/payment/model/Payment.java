package com.identify.product.FamilyKart.payment.model;

import com.identify.product.FamilyKart.order.model.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="payments")
public class Payment {

    @Id
    @GeneratedValue(strategy=jakarta.persistence.GenerationType.IDENTITY)
    private long paymentID;

    @OneToOne(mappedBy = "payment", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Order order;

    @NotBlank
    @Size(min = 3, message = "Payment method must be at least 5 characters long")
    private String paymentMethod;


    private String paymentGatewayPaymentID;

    private String paymentGatewayStatus;


    private String paymentGateWayResponseMessage;

    private String paymentGateWayName;

    public Payment(String paymentMethod,String paymentGatewayPaymentID, String paymentGatewayStatus, String paymentGateWayResponseMessage, String paymentGateWayName) {
        this.paymentMethod = paymentMethod;
        this.paymentGatewayPaymentID = paymentGatewayPaymentID;
        this.paymentGatewayStatus = paymentGatewayStatus;

        this.paymentGateWayResponseMessage = paymentGateWayResponseMessage;
        this.paymentGateWayName = paymentGateWayName;

    }


}
