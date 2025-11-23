package com.identify.product.FamilyKart.cart.payload;

import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {

    private Long cartItemID;

    private  CartResponseDTO cartResponseDTO;

    private ProductResponseDTO productResponseDTO;

    private Integer quantity; // Quantity of the product in the cart

    private double price; // Price of the product at the time it was added to the cart

    private double discount;// Discount on the product at the time it was added to the cart
}
