package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;
import org.example.project.exception.OurException;
import org.example.project.model.OrderDTO;
import org.example.project.model.Times;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.service.other.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ProductRepository productRepository;
//alindi

//        @Transactional
//        @Override
//        public Order addOrder(OrderDTO order) {
//
//            // Check if order is valid
//            if (order == null || order.getProductsSet().isEmpty()) {
//                throw new OurException("Order or products set is invalid.");
//            }
//
//            // Create Order object and set properties
//            Order orderDTO = new Order();
//            orderDTO.setProductsSet(order.getProductsSet());
//            orderDTO.setOrderDate(order.getOrderDate());
//            orderDTO.setCashier(order.getCashier());
//            orderDTO.setPaymentMethod(order.getPaymentMethod());
//
//            // Calculate total price of products
//            Long totalPrice = order.getProductsSet().stream()
//                    .mapToLong(Product::getPrice)
//                    .sum();
//
//            orderDTO.setTotalPrice(totalPrice);
//
//            // Optionally: validate products before saving
//            // Example: If you want to check if all products are valid (e.g., not already ordered)
//            for (Product product : order.getProductsSet()) {
//                List<Product> byReceiptNo = productRepository.findByReceiptNo(product.getReceiptNo());
//                if (byReceiptNo.isEmpty()) {
//                    throw new OurException("Product with receiptNo " + product.getReceiptNo() + " not found.");
//                }
//            }
//
//            // Save the order
//            orderRepository.save(orderDTO);
//            return orderDTO;
//        }

    @Transactional
        @Override
        public Order addOrder(OrderDTO order) {
        Product product = new Product();
        Order orderDTO = new Order();
        if (order != null) {
            if (!order.getProductsSet().isEmpty() )
//                orderDTO.setOrderId((order.getOrderId()));
                orderDTO.setProductsSet(order.getProductsSet());
                orderDTO.setOrderDate(order.getOrderDate());
                orderDTO.setCashier(order.getCashier());
                orderDTO.setPaymentMethod(order.getPaymentMethod());
                List<Product> productsSet = order.getProductsSet().stream().toList();
                Long price= 0L;
                for (Product p : productsSet) {
                   price=price+ p.getPrice();
                    System.out.println(price);
                   if(productRepository.findByReceiptNo(p.getReceiptNo()).isEmpty()){
                       throw  new OurException("product yoxdu");
                   }
                }


                orderDTO.setTotalPrice(price);

//              for (Product product1 : order.getProductsSet()) {
////                   if(product1!=null){
////                       List<Product> byReceiptNo = productRepository.findByReceiptNo(product1.getReceiptNo());
////                       System.out.println("rdfgthdf");
////                           if(byReceiptNo.isEmpty()) {
////                               throw new OurException("Tapilmadi ");
////                           }
////
////
////                       }
////                   }
////               }
                //productRepository.save(product);
                orderRepository.save(orderDTO);
                System.out.println("DATA elave olundu");
                return orderDTO;

            } else {
                System.out.println("melumat sehvdir");

            }


        return orderDTO;
    }


    @Override
     public Page<Order> getOrders(Integer page, Integer size, String date) {
        System.out.println( orderRepository.findAll());
        Pageable pageable = PageRequest.of(page, size);
        if(date.equalsIgnoreCase(Times.THIS_WEEK.toString())){
            LocalDate localDate=LocalDate.now();
            LocalDate with = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            return orderRepository.findAllBy(pageable,with,localDate);
         //  return orderRepository.findAll(pageable);
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
                System.out.println(this.orderRepository.findAll());
                return orderRepository.findById(orderId).get();
            }
        } catch (Exception e) {
            System.out.println("orderId is null");
            throw new IllegalArgumentException("orderId is null");
        }
        return null;
    }


//alindi
    @Transactional
    @Override
    public boolean deleteOrder(Long id) {
               Optional<Order> byOrderId = orderRepository.findByOrderId(id);

               if( byOrderId.isPresent()) {
                  orderRepository.deleteById(id);
                   System.out.println("DATA silindi");
                   return true;
               }
               else{
                   return false;
               }
    }

    @Override
    public Long getTotalIncome() {
        LocalDate now = LocalDate.now();
        LocalDate today = now.minusMonths(1);
        return orderRepository.getTotalPrice(now, today);
    }
}
