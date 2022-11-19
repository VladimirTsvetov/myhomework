package com.gb.webapp.services;

import com.gb.webapp.model.Product;
import com.gb.webapp.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    public List<Product> getAllProduct(){
        return productRepo.getAll();
    }

    public void deleteProduct(Integer id){
        productRepo.deleteProduct(id);
    }

    public void changeProductPrice(Integer productId, Integer delta) {
        Product product = productRepo.getProduct(productId);
        product.setPrice(product.getPrice() + delta);
    }

    public void addNewProduct(String title,Double price,Integer productId ){
        Product product = new Product(productId,price,title);
        productRepo.addProduct(product);
    }
}
