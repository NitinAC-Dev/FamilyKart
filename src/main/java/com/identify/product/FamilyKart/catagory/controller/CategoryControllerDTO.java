package com.identify.product.FamilyKart.catagory.controller;


import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.catagory.payload.CategoryRequestDTO;
import com.identify.product.FamilyKart.catagory.payload.CategoryResponseDTO;
import com.identify.product.FamilyKart.catagory.service.serviceimplementation.ServiceCategoryJPARepo;
import com.identify.product.FamilyKart.catagory.service.serviceimplementation.ServiceCategoryJPARepoForDTO;
import com.identify.product.FamilyKart.constanst.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryControllerDTO {
    //always interface to class
    // @Autowired // this is field injection
    //  private ServiceCategory serviceCategory;
    // private ServiceCategoryJPARepo serviceCategoryJPARepo;

    private ServiceCategoryJPARepoForDTO serviceCategoryJPARepoForDTO;

    public CategoryControllerDTO(ServiceCategoryJPARepoForDTO serviceCategoryJPARepoForDTO) {
        this.serviceCategoryJPARepoForDTO = serviceCategoryJPARepoForDTO;
    }

    //construction injection this is good practise
    /*public CategoryController(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }*/
    /*public CategoryControllerDTO(ServiceCategoryJPARepo serviceCategoryJPARepo) {
        this.serviceCategoryJPARepo = serviceCategoryJPARepo;
    }*/


    /*@GetMapping("/api/public/categories")
    public List<Category> getAllCategories() {

        return serviceCategory.getAllCategories();

    }*/
    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponseDTO> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {


        return ResponseEntity.status(HttpStatus.OK).body(serviceCategoryJPARepoForDTO.getAllCategories(pageNumber, pageSize, sortBy, sortDir));

    }


    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryRequestDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryRequestDTO category = serviceCategoryJPARepoForDTO.createCategory(categoryRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryRequestDTO> updateCategory(@Valid @RequestBody CategoryRequestDTO category, @PathVariable Long categoryId) {
        //serviceCategory.updateCategory(category); // Implement this method in the service layer
        CategoryRequestDTO category1 = serviceCategoryJPARepoForDTO.updateCategory(category, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(category1);

    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryRequestDTO> deleteCategoryById(@PathVariable Long categoryId) {


        CategoryRequestDTO categoryRequestDTO = serviceCategoryJPARepoForDTO.deleteCategoryById(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(categoryRequestDTO);

    }

    @GetMapping("/api/public/categories/{id}")
    public ResponseEntity<CategoryRequestDTO> getCategoryById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(serviceCategoryJPARepoForDTO.getCategoryByID(id));
    }
    @GetMapping("/api/public/categories/byname")
    public ResponseEntity<List<CategoryRequestDTO>> getCategoryByName(@RequestParam(name = "categoryName" ,required = false) String categoryName) {

        return ResponseEntity.status(HttpStatus.OK).body(serviceCategoryJPARepoForDTO.getCategoryByName(categoryName));
    }



/*

    @GetMapping("/api/public/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(serviceCategoryJPARepo.getCategoryByID(id));
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
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
    @DeleteMapping("/api/admin/categories/delete")
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
*/

//
//    @PutMapping("/api/admin/categories/{categoryId}")
//    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId) {
//        //serviceCategory.updateCategory(category); // Implement this method in the service layer
//        Category category1 = serviceCategoryJPARepo.updateCategory(category, categoryId);
//        return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully for "+ categoryId+" to "+category1.getCategoryName());
//
//    }

}
