package org.example.project.service.other;

import org.example.project.entity.other.Order;
import org.example.project.model.OrderDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface OrderService {
    Order addOrder(OrderDTO order);
    Page<Order> getOrders(Integer page, Integer size, String date);
    Order getOrder(Long orderId);
    boolean deleteOrder(Long orderId);
    Long getTotalIncome();

}
