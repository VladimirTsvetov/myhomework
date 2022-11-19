package com.gb.webapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Integer id;
    private Double price;
    private String title;

    public Product(Integer id, Double price, String title) {
        this.id = id;
        this.price = price;
        this.title = title;
    }
}
