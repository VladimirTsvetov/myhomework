package com.tsv.webshop.services;

import com.tsv.webshop.dtos.Cart;
import com.tsv.webshop.dtos.CartItem;
import com.tsv.webshop.entities.Order;
import com.tsv.webshop.entities.OrderItem;
import com.tsv.webshop.repositories.OrderItemRepository;
import com.tsv.webshop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }
    @Transactional
    public void saveAll(List<OrderItem> orderItems){
        for(OrderItem item: orderItems){
            orderItemRepository.save(item);
        }
    }
}
