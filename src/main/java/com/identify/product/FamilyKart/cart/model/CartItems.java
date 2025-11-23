package com.identify.product.FamilyKart.cart.model;

import com.identify.product.FamilyKart.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemID;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart; // Reference to the Cart entity
                       // Many items can belong to one cart
                       // Note: Ensure that the 'cart' field name matches the 'mappedBy' attribute in the Cart entity
                       // If the field name in Cart is 'cartItems', then 'mappedBy' should be 'cartItems'
                       // cart in line 30 of Cart.java and cart in line 20 of CartItems.java should be same
                       //private Cart cart;   @OneToMany(mappedBy = "cart"   cart is same in both places then only it will work

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Assuming Product is another entity class


    private Integer quantity; // Quantity of the product in the cart

    private double price; // Price of the product at the time it was added to the cart

    private double discount;// Discount on the product at the time it was added to the cart

}
