package com.gb.webapp.repo;

import com.gb.webapp.model.Product;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepo {
    private List<Product> products = new ArrayList<>();


    public ProductRepo(){
        products.add(new Product(1,12.0,"Lager"));
        products.add(new Product(2,12.35, "Dark Lager"));
        products.add(new Product(3,50.05,"Whisky"));
    }



    public void deleteProduct(Integer id){
        products.removeIf(p->p.getId().equals(id));
    }

    public Product getProduct(Integer id){
        return products.stream().filter(p->p.getId().equals(id)).findFirst().get();
    }

    public List<Product> getAll(){
        return products;
    }

    public void addProduct(Product p){
        products.add(p);
    }
}
