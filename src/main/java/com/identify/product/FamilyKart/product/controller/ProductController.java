package com.identify.product.FamilyKart.product.controller;

import com.identify.product.FamilyKart.config.AppConfig;
import com.identify.product.FamilyKart.constanst.AppConstants;
import com.identify.product.FamilyKart.product.model.Product;
import com.identify.product.FamilyKart.product.payload.ProductRequestDTO;
import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import com.identify.product.FamilyKart.product.service.ServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProductController {


        @Autowired
        private ServiceProduct serviceProduct;

    @PutMapping("/api/admin/products/{productID}")
    public ResponseEntity<ProductRequestDTO> updateProduct(@RequestBody ProductRequestDTO productDTO,  @PathVariable Long productID) {
        ProductRequestDTO productRequestDTO=serviceProduct.updateProductDetails(productDTO,productID);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRequestDTO);
    }

    @PutMapping("/api/admin/products/images/{productID}")
    public ResponseEntity<ProductRequestDTO> updateProductImages(@RequestParam(name = "images") MultipartFile productImage, @PathVariable Long productID) throws IOException {
        ProductRequestDTO productRequestDTO=serviceProduct.updateImages(productImage,productID);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRequestDTO);
    }


    @PostMapping("/api/admin/category/{categoryID}/products")
    public ResponseEntity<ProductRequestDTO> addProduct(@RequestBody ProductRequestDTO productDTO, @PathVariable Long categoryID) {
         ProductRequestDTO productRequestDTO=serviceProduct.addProduct(productDTO, categoryID);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRequestDTO);
    }

    @GetMapping("/api/public/products")
    public ResponseEntity<ProductResponseDTO> getAllProducts(
            @RequestParam (value="pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam( value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam( value="sortBy", defaultValue= AppConstants.DEFAULT_SORT_PRODUCT_BY, required = false) String sortBy,
            @RequestParam( value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
            ) {
        ProductResponseDTO  allProducts=serviceProduct.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

   @GetMapping("/api/public/category/{categoryID}/products")
    public ResponseEntity<ProductResponseDTO> getProductsByCategoryID(@PathVariable Long categoryID) {
        ProductResponseDTO productRequestDTO=serviceProduct.getProductsByCategoryID( categoryID);
        return ResponseEntity.status(HttpStatus.OK).body(productRequestDTO);

    }
    @GetMapping("/api/public/products/keywords/{keyWord}")
    public ResponseEntity<ProductResponseDTO> getProductByKeyword(@PathVariable String keyWord) {
        ProductResponseDTO productRequestDTO=serviceProduct.getProductByKeyword( keyWord);
        return ResponseEntity.status(HttpStatus.FOUND).body(productRequestDTO);
    }

    @DeleteMapping("/api/admin/products/delete/{productID}")
    public ResponseEntity<ProductRequestDTO> deleteProductById(@PathVariable Long productID) {
        ProductRequestDTO productRequestDTO = serviceProduct.deleteProduct(productID);
        return ResponseEntity.status(HttpStatus.OK).body(productRequestDTO);
    }
    @DeleteMapping("/api/admin/products/delete/productName/{productName}")
    public ResponseEntity<ProductRequestDTO> deleteProductByName(@PathVariable String productName) {
        ProductRequestDTO productRequestDTO = serviceProduct.deleteProduct(productName);
        return ResponseEntity.status(HttpStatus.OK).body(productRequestDTO);
    }


}
