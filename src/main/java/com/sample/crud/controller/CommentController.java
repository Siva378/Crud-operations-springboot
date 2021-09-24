package com.sample.crud.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.crud.exception.ResourceNotFoundException;
import com.sample.crud.model.Product;
import com.sample.crud.repository.ProductRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class CommentController {

	@Autowired
	ProductRepository productRepository;


	@GetMapping("/product")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Product> getAllComments() {
		return productRepository.findAll();
	}
	

	@PostMapping("/product")
	public Product createComment(@RequestBody Product product) {
		return productRepository.save(product);
	}

	@GetMapping("/product/{id}")
	public Product getCommentById(@PathVariable(value = "id") Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
	}

	@PutMapping("/product/{id}")
	public Product updateComment(@PathVariable(value = "id") Long productId, @RequestBody Product productDetails) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(" Product", "id", productId));

		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		product.setPrice(productDetails.getPrice());

		Product updatedProduct = productRepository.save(product);
		return updatedProduct;
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "id") Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(" Product", "id", productId));

		productRepository.delete( product);

		return ResponseEntity.ok().build();
	}
}
