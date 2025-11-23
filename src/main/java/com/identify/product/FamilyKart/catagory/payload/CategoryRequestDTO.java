package com.identify.product.FamilyKart.catagory.payload;


import com.identify.product.FamilyKart.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    private Long categoryID;
    private String categoryName;
    private List<Product> productList;


}
