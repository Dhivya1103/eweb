package com.eweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CategoryRepository;
import com.eweb.dao.FavoriteRepository;
import com.eweb.dao.ProductsRepository;
import com.eweb.dao.ReviewRepository;
import com.eweb.dao.SubCategoryRepository;
import com.eweb.dto.CategoryDto;
import com.eweb.dto.DashboardproductDto;
import com.eweb.dto.ProductDto;
import com.eweb.dto.RoleDto;
import com.eweb.dto.SubCategoryDto;
import com.eweb.model.Category;
import com.eweb.model.Products;
import com.eweb.model.Role;
import com.eweb.model.Status;
import com.eweb.model.SubCategory;






@Service
public class ProductService {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	SubCategoryRepository subCategoryRepository;
	@Autowired
	ProductsRepository productsRepository;
	@Autowired
	FavoriteRepository favoriteRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	public ResponseEntity<?> getLatestProducts() {
		
		return null;
		
		
	}

	public ResponseEntity<?> saveCategory(CategoryDto dto) {
		Category category = new Category(dto);
		categoryRepository.save(category);		
		 return ResponseEntity.ok(new Status("200", "role registered successfully!"));
		
	}

	public ResponseEntity<?> findAllCategory() {
		 List<Category> all = categoryRepository.findAll();
	        List<CategoryDto> collect = all.stream().map(data-> new CategoryDto(data)).collect(Collectors.toList());
	        return new ResponseEntity<>(collect, HttpStatus.OK);
	}

	public ResponseEntity<?> getSubCategory(Long categoryId) {
		List<SubCategory> subModule = subCategoryRepository.findByCategoryId(categoryId);
        if (subModule.isEmpty()) {
            return new ResponseEntity<List<SubCategoryDto>>(new ArrayList<SubCategoryDto>(), HttpStatus.OK);
        }
        List<SubCategoryDto> dto = subModule.stream().map(val -> new SubCategoryDto(val)).collect(Collectors.toList());
        return new ResponseEntity<List<SubCategoryDto>>(dto, HttpStatus.OK);
	}

	public ResponseEntity<?> deleteCategory(Long categoryId) {
		 Optional<Category> existingModule = categoryRepository.findById(categoryId);
	        if (existingModule.isPresent()) {

	            List<SubCategory> subModules = subCategoryRepository.findByCategoryId(categoryId);
	            if (!subModules.isEmpty()) {	                
	            	subCategoryRepository.deleteAll(subModules);
	            }
	            categoryRepository.deleteById(categoryId);
	            return new ResponseEntity<Status>(new Status("200", "Category deleted successfully!!!"), HttpStatus.OK);
	        }
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Status("404", "Category doesn't exist"));
	    }

	public ResponseEntity<?> deleteSubCategory(Long subCategoryId) {
		Optional<SubCategory> existingSubModule = subCategoryRepository.findById(subCategoryId);
        if (existingSubModule.isPresent()) {        
        	subCategoryRepository.deleteById(subCategoryId);
            return new ResponseEntity<Status>(new Status("200", "SubCategory deleted successfully!!!"), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Status("404", "SubCategory doesn't exist"));
    }

	public ResponseEntity<?> saveSubCategory(SubCategoryDto dto) {
		Optional<SubCategory> existingSubModule = subCategoryRepository.findByNameIgnoreCaseAndCategoryId(dto.getName(), dto.getCategoryId());
        if (!existingSubModule.isPresent()) {
        	SubCategory model = new SubCategory(dto);         	
        	subCategoryRepository.save(model);
            return new ResponseEntity<Status>(new Status("200", "SubCategory saved successfully!!!"), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Status("403", "SubCategory already exist"));
    }

	public ResponseEntity<?> saveProduct(ProductDto dto) {
		 Products product = new Products(dto);	       
	        product.setCreatedAt(LocalDateTime.now());	       
	        productsRepository.save(product);
	        return new ResponseEntity<Status>(new Status("200", "Product saved successfully!!!"), HttpStatus.OK); 
	}

	public ResponseEntity<?> updateProduct(ProductDto dto) {
		Optional<Products> products = productsRepository.findById(dto.getId());
		if(products.isPresent()) {
			 Products product = products.get();  
			 product.setName(dto.getName());
			 product.setPrice(dto.getPrice());
			 product.setStock(dto.getStock());
			 product.setDiscount(dto.getDiscount());
			 product.setCategory(dto.getCategoryId());
			 product.setSubCategory(dto.getSubCategoryId());
			 product.setImageUrl(dto.getImageUrl());
			 
		        product.setUpdatedAt(LocalDateTime.now());	       
		        productsRepository.save(product);
		}
		 return new ResponseEntity<Status>(new Status("200", "Product Updated successfully!!!"), HttpStatus.OK); 
	}

	public ResponseEntity<?> findProduct(Long productId) {
		Optional<Products> pro = productsRepository.findById(productId);
		if(pro.isPresent()) {
			DashboardproductDto product = new DashboardproductDto(pro.get());
			Double favoriteCount = favoriteRepository.favoriteCount(productId);
			product.setFavoritesCount(favoriteCount);
			Double reviewCount = reviewRepository.reviewCount(productId);
			product.setReviewsCount(reviewCount);
		}return null;
	}


}
