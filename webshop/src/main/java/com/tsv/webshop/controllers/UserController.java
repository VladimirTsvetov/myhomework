package com.tsv.webshop.controllers;

import com.tsv.webshop.entities.User;
import com.tsv.webshop.services.OrderService;
import com.tsv.webshop.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users/")
public class UserController {
    private final UserService userService;


    @GetMapping
    public List<User> getAllUsers(){
        List<User> userList = userService.findAll();
        return userList;
    };
}
