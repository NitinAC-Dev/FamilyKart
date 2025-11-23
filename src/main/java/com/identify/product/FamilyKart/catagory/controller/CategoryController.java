package com.identify.product.FamilyKart.catagory.controller;


import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.catagory.service.serviceimplementation.ServiceCategory;
import com.identify.product.FamilyKart.catagory.service.serviceimplementation.ServiceCategoryJPARepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    //always interface to class
    // @Autowired // this is field injection
  //  private ServiceCategory serviceCategory;
    private ServiceCategoryJPARepo serviceCategoryJPARepo;

    //construction injection this is good practise
    /*public CategoryController(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }*/
    public CategoryController(ServiceCategoryJPARepo serviceCategoryJPARepo) {
        this.serviceCategoryJPARepo = serviceCategoryJPARepo;
    }


    /*@GetMapping("/api/public/categories")
    public List<Category> getAllCategories() {

        return serviceCategory.getAllCategories();

    }*/
   // @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.status(HttpStatus.OK).body(serviceCategoryJPARepo.getAllCategories());

    }


   // @GetMapping("/api/public/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(serviceCategoryJPARepo.getCategoryByID(id));
    }

   // @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category categoryName) {
        serviceCategoryJPARepo.createCategory(categoryName);

        return ResponseEntity.status(HttpStatus.CREATED).body("New Catagory is added to list");

    }

   // @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId) {

        try {
            String responsebody = serviceCategoryJPARepo.deleteCategoryById(categoryId);
            //return new ResponseEntity<>(responsebody, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(responsebody);
        } catch (ResponseStatusException e) {
           // return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }

    }
   // @DeleteMapping("/api/admin/categories/delete")
    public ResponseEntity<String> deleteCategory()
    {

        try {
            String deleteAllCategory = serviceCategoryJPARepo.deleteAllCategory();
          return ResponseEntity.status(HttpStatus.OK).body(deleteAllCategory);
        }
        catch (ResponseStatusException e)
        {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }


    //@PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId) {
        //serviceCategory.updateCategory(category); // Implement this method in the service layer
        Category category1 = serviceCategoryJPARepo.updateCategory(category, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully for "+ categoryId+" to "+category1.getCategoryName());

    }

}
