package com.identify.product.FamilyKart.cart.repository;

import com.identify.product.FamilyKart.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*@Repository*/
public interface CartRepositoryJPA extends JpaRepository<Cart,Long> {


    // Method to find cart by user's email
    // The ?1 indicates the first parameter of the method
    // This assumes that the Cart entity has a relationship with a User entity that has an email field
    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1")
    Cart findCartByEmail(String email);


    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1 AND c.cartID = ?2")
    Cart findCartByEmailAndCartID(String userEmail, Long carID);


}
