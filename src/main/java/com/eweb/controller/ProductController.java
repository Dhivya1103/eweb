package com.eweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.AdminRepository;
import com.eweb.dto.CategoryDto;
import com.eweb.dto.ProductDto;
import com.eweb.dto.SubCategoryDto;
import com.eweb.model.Admin;
import com.eweb.service.ProductService;




@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private AdminRepository adminRepository;
	
	  @GetMapping("/latest-products")
	    public ResponseEntity<?> getLatestProducts() {
	      return productService.getLatestProducts();
	        
	    }
	  
	  @PostMapping("/saveCategory")
	  public ResponseEntity<?> saveCategory(@RequestBody CategoryDto dto ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
		  return productService.saveCategory(dto);
	  }
	  @GetMapping("/findAllCategory")
	    public  ResponseEntity<?>  findAllCategory(Authentication authentication) {	    
	    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
	        return productService.findAllCategory();
	    }
	  
	  @GetMapping("/getSubCategory")
		public ResponseEntity<?> getSubCategory(@RequestParam("categoryId") Long categoryId,
				Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());			
				return productService.getSubCategory(categoryId);			
		}
	  @DeleteMapping("/deleteCategory")
		public ResponseEntity<?> deleteCategory(@RequestParam("categoryId") Long categoryId, Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		
				return productService.deleteCategory(categoryId);			
		}
	  
	  @DeleteMapping("/deleteSubCategory")
		public ResponseEntity<?> deleteSubCategory(@RequestParam("subCategoryId") Long subCategoryId,
				Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());			
				return productService.deleteSubCategory(subCategoryId);		
		}
	  
	  @PostMapping("/saveSubCategory")
		public ResponseEntity<?> saveSubCategory(@RequestBody SubCategoryDto dto, Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
			if (user != null)
				return productService.saveSubCategory(dto);

			else
				return ResponseEntity.badRequest().body("User not found");
		}

	  @PostMapping("/saveProduct")
	    public ResponseEntity<?> saveProduct( @RequestBody ProductDto dto) {
	        return productService.saveProduct(dto);
	  }
	  @PutMapping("/updateProduct")
	    public ResponseEntity<?> updateProduct( @RequestBody ProductDto dto) {
	        return productService.updateProduct(dto);
	  }
	  
	  @GetMapping("/findProduct")
	    public  ResponseEntity<?>  findProduct(@RequestParam("productId") Long productId , Authentication authentication) {	    
	    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
	        return productService.findProduct(productId);
	    }
}
