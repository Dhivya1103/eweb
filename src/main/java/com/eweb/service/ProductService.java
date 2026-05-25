package com.eweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CategoryRepository;
import com.eweb.dao.FavoriteRepository;
import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.ProductsRepository;
import com.eweb.dao.ReviewRepository;
import com.eweb.dao.SubCategoryRepository;
import com.eweb.dto.CategoryDto;
import com.eweb.dto.DashboardproductDto;
import com.eweb.dto.FvoriteDto;
import com.eweb.dto.PageDataDto;
import com.eweb.dto.ProductDto;
import com.eweb.dto.ProductList;
import com.eweb.dto.ReviewDto;
import com.eweb.dto.RoleDto;
import com.eweb.dto.SubCategoryDto;
import com.eweb.dto.VariantDto;
import com.eweb.model.Category;
import com.eweb.model.ProductVariant;
import com.eweb.model.Products;
import com.eweb.model.Review;
import com.eweb.model.Role;
import com.eweb.model.Status;
import com.eweb.model.SubCategory;
import com.eweb.model.favorite;

import jakarta.transaction.Transactional;







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
	@Autowired
	ProductVariantRepository productVariantRepository;
	
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
@Transactional
	public ResponseEntity<?> saveProduct(ProductDto dto) {
		 Products product = new Products(dto);	       
	        product.setCreatedAt(LocalDateTime.now());	
	        Products savedProduct = productsRepository.save(product);
	        if( dto.getVariants()!=null &&!dto.getVariants().isEmpty()) {
	        	 for (VariantDto variant : dto.getVariants()) {
	        		 ProductVariant pVar= new ProductVariant(variant) ;
	        		 pVar.setProductId(savedProduct.getId());
	        		 productVariantRepository.save(pVar);
	        }}
	   
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
		        
		        if(!dto.getVariants().isEmpty()) {
		        	 for (VariantDto variantDto : dto.getVariants()) {
		        		 ProductVariant variant = null;

		                 if (variantDto.getId() != null) {
		                    Optional<ProductVariant> byId = productVariantRepository.findById(variantDto.getId());
		                     if(byId.isPresent()) {
		                    	 variant= byId.get();
		                    	 variant.setColor(variantDto.getColor());
		                    	 variant.setPrice(variantDto.getPrice());
		                    	 variant.setProductId(dto.getId());
		                    	 variant.setSize(variantDto.getSize());
		                    	 variant.setStock(variantDto.getStock());
		                     }
		                 } else {
		                     variant = new ProductVariant(variantDto);
		                     variant.setProductId(dto.getId());
		                 }              
		                		            
		                 productVariantRepository.save(variant);
		             }
		}}
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
			List<favorite> favorites = favoriteRepository.findByProduct(productId);
	        List<FvoriteDto> fList = null;
	        if (favorites != null && !favorites.isEmpty()) {
	            fList = favorites.stream()
	                             .map(FvoriteDto::new)
	                             .collect(Collectors.toList());
	        }
	        product.setFavorites(fList);	      
	        List<Review> reviews = reviewRepository.findByProduct(productId);
	        List<ReviewDto> rList = null;
	        if (reviews != null && !reviews.isEmpty()) {
	            rList = reviews.stream()
	                           .map(ReviewDto::new)
	                           .collect(Collectors.toList());
	        }
	        product.setReviews(rList);
	        Optional<SubCategory> byId = subCategoryRepository.findById(product.getSId());
	        if(byId.isPresent()) {
	        	product.setSubCategoryName(byId.get().getName());
	        }
	          Optional<Category> byId2 = categoryRepository.findById(product.getCId());
	        if(byId2.isPresent()) {
	        	product.setCategoryName(byId2.get().getName());
	        }
	        
	        List<ProductVariant> byProduct = productVariantRepository.findByProduct(productId);
	        if(byProduct!=null &&!byProduct.isEmpty()) {
	        	List<VariantDto> collect = byProduct.stream().map(VariantDto::new ).collect(Collectors.toList());
	        	product.setVariants(collect);
	        }
	        return new ResponseEntity<DashboardproductDto>(product, HttpStatus.OK);
		} else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with id: " + productId);
}
	}

	public ResponseEntity<?> findlatestProduct(Long categoryId, String name, Double minPrice, Double maxPrice,String size, Pageable pageable) {
		Page<ProductList> latestProduct = productsRepository.findLatestProduct(categoryId, name, minPrice, maxPrice,pageable);
	 List<DashboardproductDto> dtoList = latestProduct.stream().
				map(product -> {
			        DashboardproductDto dto = new DashboardproductDto(product);			        
			        Double favoriteCount = favoriteRepository.favoriteCount(product.getPId());
			        dto.setFavoritesCount(favoriteCount != null ? favoriteCount : 0);

			        Double reviewCount = reviewRepository.reviewCount(product.getPId());
			        dto.setReviewsCount(reviewCount != null ? reviewCount : 0);

			  
			        List<favorite> favorites = favoriteRepository.findByProduct(product.getPId());
			        List<FvoriteDto> fList = favorites != null
			                ? favorites.stream().map(FvoriteDto::new).collect(Collectors.toList())
			                : new ArrayList<>();
			        dto.setFavorites(fList);

			        List<Review> reviews = reviewRepository.findByProduct(product.getPId());
			        List<ReviewDto> rList = reviews != null
			                ? reviews.stream().map(ReviewDto::new).collect(Collectors.toList())
			                : new ArrayList<>();
			        dto.setReviews(rList);
			        List<ProductVariant> byProduct = productVariantRepository.findByProduct(product.getPId());
			        if(byProduct!=null &&!byProduct.isEmpty()) {
			        	List<VariantDto> collect = byProduct.stream().map(VariantDto::new ).collect(Collectors.toList());
			        	dto.setVariants(collect);
			        }
			        return dto;
			    }).collect(Collectors.toList());	
		 PageDataDto<DashboardproductDto> pageData = new PageDataDto<>(dtoList, latestProduct);
	        return new ResponseEntity<>(pageData, HttpStatus.OK);

		
	}

	public ResponseEntity<?> saveReview(ReviewDto review) {
		Optional<Products> product = productsRepository.findById(review.getProductId());
		if(product.isPresent()) {
			Review model = new Review(review);
			model.setCreatedAt(LocalDateTime.now());
			reviewRepository.save(model);
	return new ResponseEntity<Status>(new Status("200", "product review  Updated successfully!!!"), HttpStatus.OK);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new Status("404", "Product doesn't exist"));
		}
		
	}
	
	public ResponseEntity<?> saveFavorite(FvoriteDto review) {
		Optional<Products> product = productsRepository.findById(review.getProductId());
		if(product.isPresent()) {
			favorite model = new favorite(review);
			model.setCreatedAt(LocalDateTime.now());
			favoriteRepository.save(model);
	return new ResponseEntity<Status>(new Status("200", "product favorite  Updated successfully!!!"), HttpStatus.OK);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new Status("404", "Product doesn't exist"));
		}
		
	}
	
	public ResponseEntity<?> getUserFavorites(Long userId, Pageable pageable) {

	    Page<ProductList> favoriteProducts =
	            productsRepository.findFavoriteProductsByUser(userId, pageable);

	    List<DashboardproductDto> dtoList = favoriteProducts.stream()
	            .map(product -> {
	                DashboardproductDto dto = new DashboardproductDto(product);
	                Double favoriteCount = favoriteRepository.favoriteCount(product.getPId());
	                dto.setFavoritesCount(
	                        favoriteCount != null ? favoriteCount : 0);
	                Double reviewCount =
	                        reviewRepository.reviewCount(product.getPId());
	                dto.setReviewsCount(
	                        reviewCount != null ? reviewCount : 0);
	  			  
			        List<favorite> favorites = favoriteRepository.findByProduct(product.getPId());
			        List<FvoriteDto> fList = favorites != null
			                ? favorites.stream().map(FvoriteDto::new).collect(Collectors.toList())
			                : new ArrayList<>();
			        dto.setFavorites(fList);

			        List<Review> reviews = reviewRepository.findByProduct(product.getPId());
			        List<ReviewDto> rList = reviews != null
			                ? reviews.stream().map(ReviewDto::new).collect(Collectors.toList())
			                : new ArrayList<>();
			        dto.setReviews(rList);
			        List<ProductVariant> byProduct = productVariantRepository.findByProduct(product.getPId());
			        if(byProduct!=null &&!byProduct.isEmpty()) {
			        	List<VariantDto> collect = byProduct.stream().map(VariantDto::new ).collect(Collectors.toList());
			        	dto.setVariants(collect);
			        }
			        return dto;
			    }).collect(Collectors.toList());	
		 PageDataDto<DashboardproductDto> pageData = new PageDataDto<>(dtoList, favoriteProducts);
	        return new ResponseEntity<>(pageData, HttpStatus.OK);

	}


}
