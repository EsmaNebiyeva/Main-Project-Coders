package org.example.project.service;

import org.example.project.entity.Order;
import org.example.project.entity.Product;
import org.example.project.model.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order addOrder(OrderDTO order);
    Page<Order> getOrders(Integer page, Integer size, String date);
    Order getOrder(Long orderId);
    void deleteOrder(String orderId);

}
