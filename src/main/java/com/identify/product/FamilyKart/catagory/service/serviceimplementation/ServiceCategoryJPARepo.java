package com.identify.product.FamilyKart.catagory.service.serviceimplementation;


import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.catagory.repository.CategoryRepo;
import com.identify.product.FamilyKart.exceptionhandling.ApiException;
import com.identify.product.FamilyKart.exceptionhandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ServiceCategoryJPARepo {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepo.findAll();
        if (categoryList.isEmpty()) {
            throw new ApiException(
                    "Category list is empty in DB !!!!!"
            );
        }
        return categoryList;
    }

    public String deleteCategoryById(Long categoryId) {
        Category category = getAllCategories().stream()
                .filter(id -> id.getCategoryID().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", "Category with ID " + categoryId + " not found"));

//categoryRepo.deleteById(categoryId);
        categoryRepo.delete(category);
        //categoryList.remove(category);
        return "Category is removed for " + categoryId;


    }

    public void createCategory(Category category) {
        /*Category byCategoryName =*/ categoryRepo.findByCategoryName(category.getCategoryName());//.orElseThrow();/*.ifPresent(c -> {
        //    throw new ApiException("Category with name " + category.getCategoryName() + " already exists in DB !!!!!");
      //  });*/
//        if (byCategoryName != null) {
//            throw new ApiException("Category with name " + category.getCategoryName() + " already exists in DB !!!!!");
//        }

        categoryRepo.save(category);
    }

    public Category getCategoryByID(Long id) {

        return getAllCategories().stream()
                .filter(category -> category.getCategoryID().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));


    }

    public Category updateCategory(Category category, Long categoryId) {

        Category existingCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category with ID " + categoryId + " not found"));

        // in this we are updating the entire object which we have sent in the parameter
        //and using the id from the path variable
        category.setCategoryID(categoryId);
        ////
      /*  Category category1 = categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category with ID " + categoryId + " not found"));
        //Here we are finding by id using id from the path variable
        // once we get the id it will return the entire object
        // here we are taking the reference of entire object and we are setting the new name given in the parameter
        category1.setCategoryName(category.getCategoryName());
        existingCategory= categoryRepo.save(category1); */
        /////
        existingCategory = categoryRepo.save(category);
        return existingCategory;
    }

    public String deleteAllCategory() {

        categoryRepo.deleteAll();
        return "All Category are deleted";

    }

}
