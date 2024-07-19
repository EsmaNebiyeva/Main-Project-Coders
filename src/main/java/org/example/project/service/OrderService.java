package org.example.project.service;

import org.example.project.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void addOrder(Order order);
    Page<Order> getProducts(Integer page, Integer size);
    Optional<Order> getOrder(int orderId);
    void deleteOrder(int orderId);

}
