package com.tsv.webshop.services;

import com.tsv.webshop.dtos.Cart;
import com.tsv.webshop.dtos.CartItem;
import com.tsv.webshop.entities.Order;
import com.tsv.webshop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void addNewOrder(){
        Order order = new Order();
        Cart currentCart = cartService.getCurrentCart();
        List<CartItem> currentCartItems = currentCart.getItems();

        for(CartItem cartItem : currentCartItems){
            order.setProduct_id(cartItem.getProductId());
            order.setQuantity(cartItem.getQuantity());
            order.setPrice(cartItem.getPrice());
            order.setTotal_price(currentCart.getTotalPrice());
        }
        orderRepository.save(order);
    }
}
