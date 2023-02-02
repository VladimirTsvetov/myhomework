package com.tsv.webshop.repositories;

import com.tsv.webshop.entities.Order;
import com.tsv.webshop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
