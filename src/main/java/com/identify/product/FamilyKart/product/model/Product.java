package com.identify.product.FamilyKart.product.model;

import com.identify.product.FamilyKart.cart.model.CartItems;
import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.user.model.User;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "productTable")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @NotNull
    @Size(min = 3, max = 50, message = "Product name must be between 3 and 20 characters")
    private String productName;
    private String image;
    @NotBlank
    @NotNull
    @Size(min = 10, max = 200, message = "Product description must be between 10 and 200 characters")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;//discounted price


    //relation ship
    //one category has many product to one to many in category and many to one in product
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    //many products can be sold by one seller
    @ManyToOne
    @JoinColumn(name = "seller_id")//user is seller here in products table
    private User user;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true,fetch = FetchType.EAGER)
    //@JoinColumn(name = "product_id")// note we can't use join column because mappedBy is used which means the other side is owning side where the foreign key is present there we have to use join column
    private List<CartItems> products;//=new ArrayList<>();//note if this didn't work add =new Arraylist()
                          // List of items in the cart
                          // one product can be in many cartitems
                          // one cartitem have one product
                          // so one to many relationship
                          // note mappedBy should be same as product in CartItems.java


}
