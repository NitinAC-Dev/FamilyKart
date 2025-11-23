package com.identify.product.FamilyKart.catagory.repository;

import com.identify.product.FamilyKart.catagory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {


    List<Category> findByCategoryName(String categoryName);
}
