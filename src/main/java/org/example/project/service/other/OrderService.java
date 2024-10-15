package org.example.project.service.other;

import org.example.project.entity.other.Order;
import org.example.project.model.OrderDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    void addOrder(Order order);
    Page<Order> getOrders(String email,Integer page, Integer size, String date);
    Order getOrder(String email,Long orderId);
    boolean deleteOrder(String email,Long orderId);
    Long getTotalIncome(String email);
    Order updateOrder(String email,Order order);
    Order updateOrderCancel(String email,Order order);
    Integer countOrderByEmail(String email);
    Long getTotalTips(String email);
}
