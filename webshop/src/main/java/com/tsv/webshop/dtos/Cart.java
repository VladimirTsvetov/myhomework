package com.tsv.webshop.dtos;

import com.tsv.webshop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data

public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart(){
        this.items = new ArrayList<>();
    }
    public List<CartItem> getItems(){
        return Collections.unmodifiableList(items);
    }
    public void add(Product product){
        items.add(new CartItem(product.getId(),
                product.getTitle(),
                1,
                product.getPrice(),
                product.getPrice()));
        recalculate();
    }

    private void recalculate(){
        totalPrice = 0;
        for(CartItem item : items){
            totalPrice += item.getPrice();
        }
    }
}
