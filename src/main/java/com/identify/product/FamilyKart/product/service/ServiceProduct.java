package com.identify.product.FamilyKart.product.service;

import com.identify.product.FamilyKart.product.model.Product;
import com.identify.product.FamilyKart.product.payload.ProductRequestDTO;
import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ServiceProduct {
    ProductRequestDTO addProduct(ProductRequestDTO productDTO, Long categoryID);

    ProductResponseDTO getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ProductResponseDTO getProductsByCategoryID(Long categoryID);

    ProductResponseDTO getProductByKeyword(String keyWord);

    ProductRequestDTO updateProductDetails(ProductRequestDTO productDTO,Long productID);

    ProductRequestDTO deleteProduct(Object productID);

    ProductRequestDTO updateImages(MultipartFile productImage, Long productID) throws IOException;
}
