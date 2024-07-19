package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Order;
import org.example.project.entity.Product;
import org.example.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
@Autowired
    private final OrderRepository orderRepository;
    @Override
    public void addOrder(Order order) {
        orderRepository.save(order);
        System.out.println("DATA elave olundu");
    }


    @Override
     public Page<Order> getProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }


    @Override
    public Optional<Order> getOrder(int orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
        System.out.println("DATA silindi");
    }
}
