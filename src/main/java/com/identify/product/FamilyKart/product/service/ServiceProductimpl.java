package com.identify.product.FamilyKart.product.service;


import com.identify.product.FamilyKart.catagory.model.Category;
import com.identify.product.FamilyKart.catagory.repository.CategoryRepo;
import com.identify.product.FamilyKart.commonservices.FileServices;
import com.identify.product.FamilyKart.exceptionhandling.ApiException;
import com.identify.product.FamilyKart.exceptionhandling.ResourceNotFoundException;
import com.identify.product.FamilyKart.product.model.Product;
import com.identify.product.FamilyKart.product.payload.ProductRequestDTO;
import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import com.identify.product.FamilyKart.product.repository.ProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceProductimpl implements ServiceProduct {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServices fileServices;

    @Value("${project.image}")
    private String path;


    /**
     * @param productDTO
     * @param categoryID
     * @return
     */
    @Override
    public ProductRequestDTO addProduct(ProductRequestDTO productDTO, Long categoryID) {

        Category category = categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryID));
        //if product is found by same name we should throw exception saying product already exists.
        Product byProductName = productRepository.findByProductName(productDTO.getProductName());
        if (byProductName!=null)
        {
            throw new ApiException("Product with name "+ productDTO.getProductName()+" already exists");
        }
        Product product=modelMapper.map(productDTO,Product.class);
        if(product.getDescription()==null)
        {
            throw new ApiException("Description cannot be null or empty for "+ product.getProductName());
        }
        product.setImage("default.png");
        product.setCategory(category);

        double discountedPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(discountedPrice);


        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductRequestDTO.class);
    }


    /**
     * @param productID
     * @return
     */
    @Override
    public ProductRequestDTO updateProductDetails(ProductRequestDTO productDTO , Long productID) {


        Product productbyID = productRepository.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));
        Product product = modelMapper.map(productDTO, Product.class);
        productbyID.setProductName(product.getProductName());
        productbyID.setDescription(product.getDescription());
        productbyID.setPrice(product.getPrice());
        productbyID.setDiscount(product.getDiscount());
        double discountedPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        productbyID.setSpecialPrice(discountedPrice);
        productbyID.setQuantity(product.getQuantity());
        Product updatedProduct = productRepository.save(productbyID);
        return modelMapper.map(updatedProduct, ProductRequestDTO.class);



    }

    /**
     * @param product
     * @return
     */
    @Override
    public ProductRequestDTO deleteProduct(Object product) {
        if (product instanceof Long productID) {
            Product productByID = productRepository.findById(productID)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));
            productRepository.delete(productByID);
            return modelMapper.map(productByID, ProductRequestDTO.class);
        } else if (product instanceof String productName) {
            Product productByName = productRepository.findByProductName(productName);
            if (productByName == null) {
                throw new ResourceNotFoundException("Product", "productName", productName);
            }
            productRepository.delete(productByName);
            return modelMapper.map(productByName, ProductRequestDTO.class);
        } else {
            throw new ApiException("Invalid product identifier type. Must be Long (ID) or String (Name).");
        }

    }

    /**
     * @param productImage
     * @param productID
     * @return
     */
    @Override
    public ProductRequestDTO updateImages(MultipartFile productImage, Long productID) throws IOException {
        //get the product by id

        Product productFromDB = productRepository.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));
        //upload the image to server
        //get the file name of the uploaded images

        String fileName =fileServices.uploadImagesToServer(path,productImage);

        //set the image name to product
        productFromDB.setImage(fileName);
        //save the product
        productRepository.save(productFromDB);
        //return dto from product to dto
        return modelMapper.map(productFromDB,ProductRequestDTO.class);


    }


    /**
     * @return
     */
    @Override
    public ProductResponseDTO getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {


        if (pageNumber<0 || pageSize<=0) {
            throw new ApiException("Page number must be greater than or equal to 0 and page size must be greater than 0");
        }
        Sort sortByProducts = Sort.by(sortBy);
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sortByProducts=  sortByProducts.ascending();
        }
        else if (sortDir.equalsIgnoreCase("dsc"))

        {
            sortByProducts=  sortByProducts.descending();
        }
        else {
            throw new ApiException("Sort direction must be either 'asc' or 'dsc'");
        }

        PageRequest pageRequest = PageRequest.of(pageNumber , pageSize, sortByProducts);
         if(pageRequest.isPaged())
         {
                if(!(pageRequest.getPageNumber()<productRepository.findAll(pageRequest).getTotalPages()))
                {
                    throw new ApiException("Page number must be less than total pages "+ productRepository.findAll(pageRequest).getTotalPages());
                }
         }
         else if(pageRequest.isUnpaged())
         {
             throw new ApiException("No products found in the database");
         }


        //get all products from db
        List<Product> productList = productRepository.findAll(pageRequest).getContent();
        //if product list is empty throw exception
        if(productList.isEmpty())
        {

            throw new ApiException("No products found in the database");
        }

        List<ProductRequestDTO> productResponseDTOStream = productList.stream().map(product -> modelMapper.map(product, ProductRequestDTO.class)).toList();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProducts(productResponseDTOStream);
        productResponseDTO.setPageNumber(pageRequest.getPageNumber());
        productResponseDTO.setPageSize(pageRequest.getPageSize());
        productResponseDTO.setTotalElements(productRepository.count());
        productResponseDTO.setTotalPages((int) Math.ceil((double) productRepository.count() / pageRequest.getPageSize()));
        productResponseDTO.setLastPage(productResponseDTO.getPageNumber().equals(productResponseDTO.getTotalPages()));

        return productResponseDTO;
    }

    /**
     * @param categoryID
     * @return
     */
    @Override
    public ProductResponseDTO getProductsByCategoryID(Long categoryID) {

        if (categoryID == null) {
            throw new ApiException("Category ID cannot be string");
        }

        if(categoryID<=0)
        {
            throw new ApiException("Category ID cannot be negative or zero");
        }
        Category category = categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryID));

        List<Product> byCategory = productRepository.findByCategoryOrderByPriceDesc(category);
        List<ProductRequestDTO> productRequestDTOList = byCategory.stream().map(product -> modelMapper.map(product, ProductRequestDTO.class)).toList();

        ProductResponseDTO productResponseDTO1 = new ProductResponseDTO();
        productResponseDTO1.setProducts(productRequestDTOList);
        return productResponseDTO1;


        //convert list of product to productResponseDTO


    }

    /**
     * @param keyWord
     * @return
     */
    @Override
    public ProductResponseDTO getProductByKeyword(String keyWord) {

        List<Product> productNameOrDescriptionLikeIgnoreCase = productRepository.findByProductNameLikeIgnoreCase('%'+keyWord+'%');


        List<ProductRequestDTO> productRequestDTOList = productNameOrDescriptionLikeIgnoreCase.stream().map(product -> modelMapper.map(product, ProductRequestDTO.class)).toList();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProducts(productRequestDTOList);
        return productResponseDTO;
    }



}
