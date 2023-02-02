package com.tsv.webshop.controllers;

import com.tsv.webshop.dtos.Cart;
import com.tsv.webshop.dtos.JwtRequest;
import com.tsv.webshop.entities.User;
import com.tsv.webshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders/")
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public void addNewOrder(@RequestBody User user){

        orderService.addNewOrder(user.getUsername());
    };
}
