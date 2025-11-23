package com.identify.product.FamilyKart.user.model;

import com.identify.product.FamilyKart.address.model.Address;
import com.identify.product.FamilyKart.cart.model.Cart;
import com.identify.product.FamilyKart.product.model.Product;
import com.identify.product.FamilyKart.roles.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_email", columnNames = "email"),
                @UniqueConstraint(name = "unique_user_name", columnNames = "username")
        }
)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @NotBlank
    @Size(min = 5, max = 15)
    private String username;


    @NotBlank
    @Size(min = 10, max = 50)
    @Email
    private String email;

    @NotBlank
    //@Size(min = 8, max = 14)
    private String password;

    public User(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }


    @Setter
    @Getter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    //eager type is used because roles are needed whenever user is fetched from db no need to separate call to fetch roles
    @JoinTable(name = "user_role"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> setOfRoles = new HashSet<>();
    //multiple users can have multiple roles like user might be admin and customer both so he should have both roles
    //multiple roles can be assigned to multiple users


    //which has mapped by is the owner of the relationship where the foreign key is present
    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    //orphanRemoval is used to remove the product if user is deleted from db or user is no longer the seller of that product
    private Set<Product> setOfProducts;



    @Setter
    @Getter
    @OneToMany(mappedBy = "users",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<Address> addresses=new java.util.ArrayList<>();

    @ToString.Exclude  //to avoid circular reference during toString() call
                       //one user can have only one cart and one cart belongs to one user
                       //cart(in User class line not 89@OneToOne(mappedBy = "user") is owner of the relationship because it is mapped by user so cart is responsible for maintaining the foreign key
                       //

    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private Cart cart;


}
