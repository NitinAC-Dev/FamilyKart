package com.identify.product.FamilyKart.catagory.service.serviceimplementation;


import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.catagory.payload.CategoryRequestDTO;
import com.identify.product.FamilyKart.catagory.payload.CategoryResponseDTO;
import com.identify.product.FamilyKart.catagory.repository.CategoryRepo;
import com.identify.product.FamilyKart.exceptionhandling.ApiException;
import com.identify.product.FamilyKart.exceptionhandling.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ServiceCategoryJPARepoForDTO implements ServiceCategory {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryResponseDTO getAllCategories
            (Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        if (pageNumber <= 0 || pageSize <= 0)
            throw new ApiException("Page Number " + pageNumber + " is not valid !!!!! page starts from 1");
        Sort sort = Sort.by(sortBy);
        if (sortDir.equalsIgnoreCase("asc")) sort = sort.ascending();
        else if (sortDir.equalsIgnoreCase("dsc")) sort = sort.descending();
        else throw new ApiException(" Sort Direction " + sortDir + " is not valid !!!!! it should be asc or dsc");
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<Category> page = categoryRepo.findAll(pageable);
        if (page.isEmpty()) throw new ApiException("Category list is empty in DB !!!!!");

        if (!(page.getNumber() < page.getTotalPages()))
            throw new ApiException("Page Number " + pageNumber + " is not valid !!!!! page not should not be greater than total pages " + page.getTotalPages());

        List<Category> categoryList = page.getContent();


        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        List<CategoryRequestDTO> categoryResponseDTOList = categoryList.stream().
                map(category -> modelMapper.map(category, CategoryRequestDTO.class)).toList();
        //from db layer     To presentation layer
        categoryResponseDTO.setCategories(categoryResponseDTOList);
        categoryResponseDTO.setPageNumber(page.getNumber());
        categoryResponseDTO.setPageSize(page.getSize());
        categoryResponseDTO.setTotalElements(page.getTotalElements());
        categoryResponseDTO.setTotalPages(page.getTotalPages());
        categoryResponseDTO.setLastPage(page.isLast());
        return categoryResponseDTO;
    }


    public CategoryRequestDTO deleteCategoryById(Long categoryId) {
//        Category category = getAllCategories().stream()
//                .filter(id -> id.getCategoryID().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", "Category with ID " + categoryId + " not found"));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", categoryId));
        categoryRepo.delete(category);
        return modelMapper.map(category, CategoryRequestDTO.class);
        // categoryRepo.delete(category);
        //categoryList.remove(category);
        //return "Category is removed for " + categoryId;


    }

    public CategoryRequestDTO createCategory(CategoryRequestDTO categoryDTO) {
        //from presentation layer ,  to db layer
        Category category = modelMapper.map(categoryDTO, Category.class);

        // here we are checking if the category name already exists in the db or not
        List<Category> byCategoryName = categoryRepo.findByCategoryName(category.getCategoryName());
        if (byCategoryName != null && !byCategoryName.isEmpty()) {
            throw new ApiException("Category with name " + category.getCategoryName() + " already exists in DB !!!!!");
        }

     // categoryRepo.findByCategoryName(category.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryName", category.getCategoryName()));
        /*.ifPresent(c -> {
            throw new ApiException("Category with name " + category.getCategoryName() + " already exists in DB !!!!!");
        });*/
        /*if (byCategoryName != null) {
            throw new ApiException("Category with name " + category.getCategoryName() + " already exists in DB !!!!!");
        }*/


        Category savedCategory = categoryRepo.save(category);
        // from db layer  ,   To presentation layer
        return modelMapper.map(savedCategory, CategoryRequestDTO.class);
    }

    public CategoryRequestDTO getCategoryByID(Long id) {

        /*return getAllCategories().stream()
                .filter(category -> category.getCategoryID().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));
*/
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));

        return modelMapper.map(category, CategoryRequestDTO.class);
    }
    public List<CategoryRequestDTO> getCategoryByName(String categoryName) {

        /*return getAllCategories().stream()
                .filter(category -> category.getCategoryID().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));

         */
        List<Category> byCategoryName = categoryRepo.findByCategoryName(categoryName);
        if (byCategoryName == null || byCategoryName.isEmpty()) {
            throw new ResourceNotFoundException("Category", "CategoryName", categoryName);
        }
        List<CategoryRequestDTO> list = byCategoryName.stream().
                map(eachcategory -> modelMapper.map(eachcategory, CategoryRequestDTO.class)).toList();
        return  list;

    }

    public CategoryRequestDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long categoryId) {


        Category category = modelMapper.map(categoryRequestDTO, Category.class);

        Category existingCategory = categoryRepo.findById(categoryId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category with ID " + categoryId + " not found"));

        if (existingCategory == null) {
            throw new ResourceNotFoundException("Category", "CategoryID", categoryId);
        }
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
        Category savedcategory = categoryRepo.save(category);
        return modelMapper.map(savedcategory, CategoryRequestDTO.class);
    }

    public String deleteAllCategory() {

        categoryRepo.deleteAll();
        return "All Category are deleted";

    }

}
