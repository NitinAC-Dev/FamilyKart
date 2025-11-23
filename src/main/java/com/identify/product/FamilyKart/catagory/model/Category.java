package com.identify.product.FamilyKart.catagory.model;

import com.identify.product.FamilyKart.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "categoryTable")
public class Category {





   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long categoryID;
   @NotBlank
   @Size(min = 3, max = 20, message = "Category name must be between 3 and 20 characters")
   private String categoryName;

   @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<Product> productList;
}
