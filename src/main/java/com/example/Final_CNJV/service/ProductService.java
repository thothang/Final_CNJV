package com.example.Final_CNJV.service;

import com.example.Final_CNJV.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);

    public List<Product> getALlProduct();

    public Boolean deleteProduct(int id);

    public Product getProductById(int id);

    public Product updateProduct(Product product, MultipartFile file);

    public List<Product> getALlActiveProduct(String category);
}
