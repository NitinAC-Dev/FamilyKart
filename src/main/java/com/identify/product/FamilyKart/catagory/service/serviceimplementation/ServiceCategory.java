package com.identify.product.FamilyKart.catagory.service.serviceimplementation;

import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.catagory.payload.CategoryRequestDTO;
import com.identify.product.FamilyKart.catagory.payload.CategoryResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceCategory {

    public CategoryRequestDTO deleteCategoryById(Long categoryId) ;

    public CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    public CategoryRequestDTO createCategory(CategoryRequestDTO categoryName);

   public  CategoryRequestDTO getCategoryByID(Long id);

   public List<CategoryRequestDTO> getCategoryByName(String name);

    CategoryRequestDTO updateCategory(CategoryRequestDTO category, Long id);

    String deleteAllCategory();
}
