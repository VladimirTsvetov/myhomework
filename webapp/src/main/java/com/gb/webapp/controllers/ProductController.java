package com.gb.webapp.controllers;

import com.gb.webapp.model.Product;
import com.gb.webapp.repo.ProductRepo;


import com.gb.webapp.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("/products/delete/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
    }

    @GetMapping("/products/change_price")
    public void changeProductPrice(@RequestParam Integer productId,
                                   @RequestParam Integer delta){
        productService.changeProductPrice(productId,delta);
    }

    @GetMapping("/products/add")
    public void addNewProduct(@RequestParam String title,
                              @RequestParam Double price,
                              @RequestParam Integer id){
        productService.addNewProduct(title, price, id);
    }

}
