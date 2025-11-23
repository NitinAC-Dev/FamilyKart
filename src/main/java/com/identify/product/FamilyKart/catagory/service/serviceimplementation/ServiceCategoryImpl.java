package com.identify.product.FamilyKart.catagory.service.serviceimplementation;

import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.constanst.AppRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.identify.product.FamilyKart.constanst.AppRole.ADMIN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ServiceCategoryImpl {

    List<Category> categoryList = new ArrayList<>();
    private Long nextID=1L;

    /**
     * @param categoryId
     */
    /*@Override*/
    public String deleteCategoryById(Long categoryId) {

        Category category = categoryList.stream()
                .filter(id -> id.getCategoryID().equals(categoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found for "+categoryId));


        categoryList.remove(category);
        return "Category is removed for "+categoryId;




    }

    /**
     * @return
     */

    public List<Category> getAllCategories() {
        return categoryList;
    }

    /**
     * @param category
     */

    public void createCategory(Category category) {
        category.setCategoryID(nextID++);
        categoryList.add(category);
    }

    /**
     * @param id
     * @return
     */

    public Category getCategoryByID(Long id) {
        return categoryList.stream()
                .filter(category -> category.getCategoryID().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Category with ID " + id + " not found"));


    }

    /**
     * @param category
     */
    /*@Override*/
    public void updateCategory(Category category, Long categoryId) {
        Category existingCategory = categoryList.stream()
                .filter(cat -> cat.getCategoryID().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category with ID " + category.getCategoryID() + " not found"));

        existingCategory.setCategoryName(category.getCategoryName());

    }

    /**
     *
     */
  /*  @Override*/
    public String deleteAllCategory() {

        categoryList.clear();
        return "All Category are deleted";

    }
}
