package org.example.project.service;

import org.example.project.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order addOrder(Order order);
    Page<Order> getOrders(Integer page, Integer size);
    Order getOrder(Long orderId);
    void deleteOrder(Long orderId);

}
