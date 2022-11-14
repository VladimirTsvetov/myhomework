package com.example.shop.services;

import com.example.shop.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    List<Product> products = new ArrayList<>();

    public List<Product> findAll(){
        return products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public Product getProduct(int productId){
        for(Product p: products){
            if(p.getId() == productId)return p;
        }
        return null;

    }
}
