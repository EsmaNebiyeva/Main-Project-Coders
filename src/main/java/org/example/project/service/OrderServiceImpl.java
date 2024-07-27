package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Order;
import org.example.project.exception.OurException;
import org.example.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
@Autowired
    private final OrderRepository orderRepository;

   @Transactional
    @Override
    public Order addOrder(Order order) {
       if(order != null) {
           orderRepository.save(order);
           System.out.println("DATA elave olundu");
           return order;
       }
       System.out.println("melumat sehvdir");
       throw new OurException("melumat sehvdir");
    }


    @Override
     public Page<Order> getOrders(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }


    @Override
    public Order getOrder(Long orderId) {
        try {
            if (orderId != null) {
                return orderRepository.findById(orderId).get();
            }
        } catch (Exception e) {
            System.out.println("orderId is null");
            throw new IllegalArgumentException("orderId is null");
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
       try {
           if (orderId != null) {
               orderRepository.deleteById(orderId);
               System.out.println("DATA silindi");
           }
       } catch (Exception e) {
           System.out.println("Order id is null && Order is not found");
       }

    }
}
