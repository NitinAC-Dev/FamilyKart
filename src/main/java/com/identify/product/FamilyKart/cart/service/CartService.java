package com.identify.product.FamilyKart.cart.service;

import com.identify.product.FamilyKart.cart.payload.CartResponseDTO;
import com.identify.product.FamilyKart.security.jwt.payload.MessageResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CartService {


    CartResponseDTO addProductToCart(Long productID, Integer quantity);

    List<CartResponseDTO> getListOfCarts();

    CartResponseDTO getUserCart(String userEmail, Long carID);



    @Transactional
    CartResponseDTO updateProductQuantityInCart(Long productID, Integer quantityOperation);


    MessageResponse deleteProductFromCart(Long cartID, Long productID);
}
