package com.identify.product.FamilyKart.order.model;

import com.identify.product.FamilyKart.address.model.Address;
import com.identify.product.FamilyKart.payment.model.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
    public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long orderID;

    @Email
    @Column(nullable = false)
    private String email;

    private String orderStatus;

    private double orderTotal;


    // unidirectional mapping
    // one order is linked to one payment
   @OneToOne
    @JoinColumn(name="paymentID")
    private Payment payment;

    // bidirectional mapping
    // mappedBy should be same as the variable name in OrderItems class
    //ONe order can have multiple order items
    //
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<OrderItems> orderItems=new ArrayList<>();

    private LocalDate orderDate;


    //order is liked to one shipping address but many orders can have same shipping address
    //
    @ManyToOne
    @JoinColumn(name="addressID")
    private Address address;

}
