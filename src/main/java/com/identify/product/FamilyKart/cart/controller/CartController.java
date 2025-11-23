package com.identify.product.FamilyKart.cart.controller;

import com.identify.product.FamilyKart.cart.payload.CartResponseDTO;
import com.identify.product.FamilyKart.cart.service.CartService;
import com.identify.product.FamilyKart.security.jwt.payload.MessageResponse;
import com.identify.product.FamilyKart.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    private CartService cartService;
    private AuthUtils authUtils;


    public CartController(CartService cartService, AuthUtils authUtils) {
        this.cartService = cartService;
        this.authUtils = authUtils;
    }

    @PostMapping("/carts/products/{productID}/quantity/{quantity}")
    public ResponseEntity<?> addToCart(@PathVariable Long productID,
                                       @PathVariable Integer quantity)
    {

        CartResponseDTO cartResponseDTO=cartService.addProductToCart(productID,quantity);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponseDTO);

    }
   // this is for getting all carts products
    @GetMapping("/carts")
    public ResponseEntity<?> getOfCart()
    {

        List<CartResponseDTO> getAllCart=cartService.getListOfCarts();
        return ResponseEntity.status(HttpStatus.FOUND).body(getAllCart);
    }

    @GetMapping("/user/cart")
    public ResponseEntity<CartResponseDTO> getUserCart() {

               String userEmail=authUtils.getLoggedInUserEmail();
                Long  carID=authUtils.getLoggedInUserCartID();
        CartResponseDTO cartResponseDTO=cartService.getUserCart(userEmail,carID);

        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDTO);
    }

    // this is like in car we have remove or add product quantity like - 1 +   if we press -- then it will delete one quantity
    //if we press  + then it will add one quantity if it come to 0 it will remove the product from the cart
    @PutMapping("/carts/products/{productID}/quantity/{operations}")
    public ResponseEntity<?> updateCartProduct(@PathVariable Long productID,
                                               @PathVariable String operations)
    {

        CartResponseDTO cartResponseDTO=cartService
                .updateProductQuantityInCart(productID,
                        operations.equalsIgnoreCase("delete") ? -1 : 1);

        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDTO);
    }

    @DeleteMapping("/carts/{cartID}/products/{productID}")
    public ResponseEntity<?> removeProductFromCart(
            @PathVariable Long cartID,
            @PathVariable Long productID)
    {

        MessageResponse messageResponse = cartService
                .deleteProductFromCart(cartID, productID);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }


}
