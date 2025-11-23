package com.identify.product.FamilyKart.cart.model;

import com.identify.product.FamilyKart.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.generator.values.GeneratedValues;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartID;


    @OneToOne         //cart should have user to identify which user the cart belongs to
    @JoinColumn(name="user_id", referencedColumnName = "userID")
    private User user; // Assuming User is another entity class and you have a relationship set up
                       //One user can have One cart ex: in Flipkart when you logged in you can see your cart which is one cart so we are using @OneToOne mapping

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},orphanRemoval = true)
    private List<CartItems> cartItems=new ArrayList<>();//note if this didn't work add =new Arraylist()
                                      // List of items in the cart

    private double totalPrice=0.0; // Total price of items in the cart
}
