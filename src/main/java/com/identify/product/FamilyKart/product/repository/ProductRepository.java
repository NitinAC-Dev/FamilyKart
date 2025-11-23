package com.identify.product.FamilyKart.product.repository;

import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.product.model.Product;
import com.identify.product.FamilyKart.product.payload.ProductRequestDTO;
import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceDesc(Category category);
    List<Product> findByProductNameLikeIgnoreCase(String keyword);

    Product findByProductName(String productName);
}
