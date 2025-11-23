package com.identify.product.FamilyKart.order.model;


import com.identify.product.FamilyKart.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//single item in the order
@Entity
@Table(name="order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {

    @Id
    @GeneratedValue(strategy=jakarta.persistence.GenerationType.IDENTITY)
    private long orderItemsID;

//bidirectional mapping
    //many order items can be of same product
    //many people in the world can order the same product
    @ManyToOne
    @JoinColumn(name="productID")
    //we need to have the product information here to know which product is ordered like which item represents like which product item is ordered
    private Product product;

//many order items can be of same order
    @ManyToOne
    @JoinColumn(name="orderID")
    private Order order;


    private Integer quantity;

    private double discount;

    private double orderProductPrice;
    // total price for this order item = quantity * orderProductPrice - discount

}
