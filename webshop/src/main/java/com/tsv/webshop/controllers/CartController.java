package com.tsv.webshop.controllers;

import com.tsv.webshop.dtos.Cart;
import com.tsv.webshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")

public class CartController {
    private final CartService cartService;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id){
        cartService.add(id);
    }

    @GetMapping("/")
    public Cart getCurrentCart(){
        return cartService.getCurrentCart();
    }
}
