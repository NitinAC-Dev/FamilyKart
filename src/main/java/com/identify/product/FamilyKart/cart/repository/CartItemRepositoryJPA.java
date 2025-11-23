package com.identify.product.FamilyKart.cart.repository;

import com.identify.product.FamilyKart.cart.model.Cart;
import com.identify.product.FamilyKart.cart.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*@Repository*/
public interface CartItemRepositoryJPA extends JpaRepository<CartItems,Long> {
    @Query("SELECT ci FROM CartItems ci WHERE ci.cart.cartID = ?1 AND ci.product.productId= ?2")
    CartItems findByCartItemByCartIdAndProductId(Long cartID, Long productID);

    @Modifying
    @Query("DELETE FROM CartItems ci WHERE ci.cart.cartID =?1 AND ci.product.productId =?2")
    void deleteCartItemByCartIDAndProductID(Long cartID, Long productID);
}
