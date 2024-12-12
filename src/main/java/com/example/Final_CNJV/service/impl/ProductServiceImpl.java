package com.example.Final_CNJV.service.impl;

import com.example.Final_CNJV.model.Category;
import com.example.Final_CNJV.model.Product;
import com.example.Final_CNJV.repository.ProductRepository;
import com.example.Final_CNJV.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getALlProduct() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(int id) {
        Product product = productRepository.findById(id).orElse(null);
        if(!ObjectUtils.isEmpty(product)){
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);

    }

    @Override
    public Product updateProduct(Product product, MultipartFile image){

        Product dbProduct = getProductById((product.getId()));
        String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(imageName);
        dbProduct.setDiscount(product.getDiscount());
        dbProduct.setIsActive(product.getIsActive());

        Double discount = product.getPrice() *(product.getDiscount()/100.0);
        double discountPrice = product.getPrice() - discount;
        dbProduct.setDiscountPrice(discountPrice);

        Product updateProduct = productRepository.save(dbProduct);

        if(!ObjectUtils.isEmpty(updateProduct)){
            if (!image.isEmpty()) {
                try {
                    File saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsoluteFile() + File.separator + "product_img" + File.separator + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                dbProduct.setImage(imageName);
            }
            return product;
        }
        return null;
    }

    @Override
    public List<Product> getALlActiveProduct(String category) {
        List<Product> products = null;
        if(ObjectUtils.isEmpty(category)){
            products = productRepository.findByIsActiveTrue();
        }else{
            products = productRepository.findByCategory(category);
        }
        return products;
    }
}
