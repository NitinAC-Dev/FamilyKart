package com.identify.product.FamilyKart.order.payload;


import com.identify.product.FamilyKart.product.payload.ProductRequestDTO;
import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long orderItemsID;


    private ProductRequestDTO product;

    private int quantity;

    private double orderProductPrice;


    private double discount;


}
