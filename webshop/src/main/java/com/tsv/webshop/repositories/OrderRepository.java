package com.tsv.webshop.repositories;

import com.tsv.webshop.entities.Order;
import com.tsv.webshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
