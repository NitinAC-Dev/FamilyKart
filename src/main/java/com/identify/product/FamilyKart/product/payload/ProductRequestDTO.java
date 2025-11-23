package com.identify.product.FamilyKart.product.payload;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {


    /* @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Long productId;
    private String productName;
    private String productDescription;//this name should be the same name of what we give in postman because this is presentation layer
    private String image;

    private Integer quantity;
    private double price;

    private double discount;
    private double specialPrice;
    private Long categoryId;

}
