package com.example.Final_CNJV.repository;

import com.example.Final_CNJV.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product , Integer> {

    public List<Product> findByIsActiveTrue();

    List<Product> findByCategory(String category);
}
