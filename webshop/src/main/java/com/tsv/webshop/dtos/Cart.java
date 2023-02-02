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
        CartItem item = findById(product.getId());
        if(item != null){
            item.setQuantity(item.getQuantity()+1);
        }else {
            items.add(new CartItem(product.getId(),
                    product.getTitle(),
                    1,
                    product.getPrice(),
                    product.getPrice()));
        }
        recalculate();
    }

    private void recalculate(){
        totalPrice = 0;
        for(CartItem item : items){
            totalPrice += item.getPrice()*item.getQuantity();
        }
    }

    public void deleteItem(Long id) {
        CartItem item = findById(id);
        if(item != null && item.getQuantity() > 1){
            item.setQuantity(item.getQuantity()-1);
        }else if(!items.isEmpty()){
            items.remove(findById(id));
        }
        recalculate();
    }

    private CartItem findById(Long id){
        if(!items.isEmpty()){
            for(CartItem item : items){
                if(id.equals(item.getProductId()))
                    return item;
            }
        }
        return null;
    }

    public void clear(){
        items.clear();
        totalPrice = 0;
    }

    public void removeById(Long id) {
        items.removeIf(item->item.getProductId().equals(id));
        recalculate();
    }
}
