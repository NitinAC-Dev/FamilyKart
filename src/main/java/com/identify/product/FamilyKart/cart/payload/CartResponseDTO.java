package com.identify.product.FamilyKart.cart.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.identify.product.FamilyKart.product.payload.ProductRequestDTO;
import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {



    private Long cartID;

    private double totalPrice=0.0;

    private List<ProductRequestDTO> listOfProducts=new ArrayList<>();//;// List of products in the cart
                                                    // if didn't work add =new ArrayList<>();
}
