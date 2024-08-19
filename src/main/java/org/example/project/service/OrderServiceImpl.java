package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Order;
import org.example.project.entity.Product;
import org.example.project.exception.OurException;
import org.example.project.model.OrderDTO;
import org.example.project.model.Times;
import org.example.project.repository.OrderRepository;
import org.example.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.lang.Character.toLowerCase;
import static org.example.project.model.ProductDTO.fromDTOToNormal;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
@Autowired
    private final OrderRepository orderRepository;
private final ProductRepository productRepository;
   @Transactional
    @Override
    public Order addOrder(OrderDTO order) {

       Product product = new Product();
       Order orderDTO = new Order();
       if (order != null) {
           if (!order.getProductsSet().isEmpty()) {
               orderDTO.setOrderId((order.getOrderId()));
               orderDTO.setProductsSet(order.getProductsSet());
               orderDTO.setOrderDate(order.getOrderDate());
               orderDTO.setCashier(order.getCashier());
               orderDTO.setPaymentMethod(order.getPaymentMethod());

//               for (Product product1 : order.getProductsSet()) {
//                   if(product1!=null){
//                       List<Product> byReceiptNo = productRepository.findByReceiptNo(product1.getReceiptNo());
//                       System.out.println("rdfgthdf");
//                           if(byReceiptNo.isEmpty()) {
//                               throw new OurException("Tapilmadi ");
//                           }
//
//
//                       }
//                   }
//               }
               //productRepository.save(product);
               orderRepository.save(orderDTO);
               System.out.println("DATA elave olundu");
               return orderDTO;

           } else {
               System.out.println("melumat sehvdir");

           }

       }return orderDTO;
   }


    @Override
     public Page<Order> getOrders(Integer page, Integer size, String date) {
        Pageable pageable = PageRequest.of(page, size);
        if(date.equalsIgnoreCase(Times.THIS_WEEK.toString())){
            LocalDate localDate=LocalDate.now();
            LocalDate with = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            return orderRepository.findAllBy(pageable,with,localDate);
       } else if(date.equalsIgnoreCase(Times.THIS_MONTH.toString())){
            LocalDate localDate=LocalDate.now();
            LocalDate minus = localDate.minusDays(localDate.getDayOfMonth());
            return orderRepository.findAllBy(pageable,minus,localDate);
        }
        else if(date.equalsIgnoreCase(Times.THIS_YEAR.toString())){
            LocalDate localDate=LocalDate.now();
            LocalDate minus = localDate.minusDays(localDate.getDayOfYear());
            return orderRepository.findAllBy(pageable,minus,localDate);
        }
        else {
            return orderRepository.findAll(pageable);
        }
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
    public void deleteOrder(String orderId) {
       try {
           if (orderId != null) {

               Optional<Order> byOrderId = orderRepository.findByOrderId(orderId);
               if( byOrderId.isPresent()) {
                   orderRepository.deleteByOrderId(orderId);
                   System.out.println("DATA silindi");
               }
               else{
                   System.out.println("melumat sehvdir");
               }
           }
       } catch (Exception e) {
           System.out.println("Order id is null");
       }

    }
}
