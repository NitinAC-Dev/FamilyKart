package com.identify.product.FamilyKart.address.model;


import com.identify.product.FamilyKart.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")    
public class Address {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long addressID;

    @NotBlank
    @Size(min = 5, message = "Street name must be at least 5 characters long")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Building name must be at least 5 characters long")
    private String buildingName;


    @NotBlank
    @Size(min = 5, message = "City name must be at least 5 characters long")
    private String city;


    @NotBlank
    @Size(min = 5, message = "State name must be at least 5 characters long")
    private String state;

    @NotBlank
    @Size(min = 5, message = "Country name must be at least 5 characters long")
    private String country;


    @NotBlank
    @Size(min = 5, message = "Zip code must be at least 5 characters long")
    private String zipCode;


    public Address(String street, String buildingName, String city, String state, String country, String zipCode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User users;//=new ArrayList<>();

}
