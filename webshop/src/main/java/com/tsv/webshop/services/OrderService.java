package com.tsv.webshop.services;

import com.tsv.webshop.dtos.Cart;
import com.tsv.webshop.dtos.CartItem;
import com.tsv.webshop.entities.Order;
import com.tsv.webshop.entities.OrderItem;
import com.tsv.webshop.entities.Product;
import com.tsv.webshop.entities.User;
import com.tsv.webshop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
    @Transactional
    public void addNewOrder(String userName){
        User user = userService.findByUsername(userName).get();

        Order order = new Order();
        order.setUser(user);


        Cart currentCart = cartService.getCurrentCart();
        order.setTotal_price(currentCart.getTotalPrice());

        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item: currentCart.getItems()){
           OrderItem orderItem = new OrderItem();
           orderItem.setOrder(order);
           orderItem.setPrice(item.getPrice());
           Product product = productService.findById(item.getProductId()).get();
           orderItem.setProduct(product);
           orderItem.setQuantity(item.getQuantity());
           orderItem.setPricePerProduct(item.getPricePerProduct());
           orderItems.add(orderItem);

        }
        orderItemService.saveAll(orderItems);
        order.setItems(orderItems);

        orderRepository.save(order);
    }
}
