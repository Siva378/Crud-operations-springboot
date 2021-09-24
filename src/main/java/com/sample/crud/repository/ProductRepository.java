package com.sample.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.sample.crud.model.Product;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {

}
