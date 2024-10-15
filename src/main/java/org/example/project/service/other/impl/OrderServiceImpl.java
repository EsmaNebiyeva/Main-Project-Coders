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

import static org.example.project.model.OrderDTO.converToDTO;


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
public boolean addOrder(Order order) {
    if (order == null) {
        System.out.println("Sifariş boşdur");
        return false;
    }else {

        List<Product> products = order.getProductsSet().stream().toList();
        System.out.println(products);

        if (products.isEmpty()) {
            System.out.println("Məhsul siyahısı boşdur");
            return false;
        } else {

            Long totalPrice = 0L;

            for (Product product : products) {
                Product existingProduct = productRepository.findByReceiptNoAndEmail(product.getReceiptNo(), order.getUser().getEmail());
                try {
                    if (existingProduct == null) {
                        throw new Exception("data yoxdur");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);

                }

                        totalPrice = totalPrice + product.getPrice() + product.getPrice() * product.getTax() - product.getDiscount() * product.getPrice();


            }

            order.setTotalPrice(totalPrice);
            orderRepository.save(order);
            return true;
        }


    }

}
//    @Transactional
//        @Override
//        public Order addOrder(Order order) {
//        Product product = new Product();
//        if (order != null) {
//            List<Product> list = order.getProductsSet().stream().toList();
//
//            if (!list.isEmpty() ) {
//                Long price = 0L;
//                for (Product p : list) {
//                    price = price + p.getPrice();
//                    System.out.println(price);
//                    if (productRepository.findByReceiptNo(p.getReceiptNo()) == null) {
//                        return null;
//                    }
//                }
//                order.setTotalPrice(price);
//                //productRepository.save(product);
//                orderRepository.save(order);
//                System.out.println("DATA elave olundu");
//                return order;
//            }
//            } else {
//            System.out.println("melumat sehvdir");
//
//        }
//        return null;
//    }


    @Override
    public Page<Order> getOrders(String email,Integer page, Integer size, String date) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail .isEmpty()) {
           // System.out.println(orderRepository);
            Pageable pageable = PageRequest.of(page, size);
            if (date.equalsIgnoreCase(Times.THIS_WEEK.toString())) {
                LocalDate localDate = LocalDate.now();
                LocalDate with = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                return orderRepository.findAllBy(pageable, with, localDate,email);
                //  return orderRepository.findAll(pageable);
            } else if (date.equalsIgnoreCase(Times.THIS_MONTH.toString())) {
                LocalDate localDate = LocalDate.now();
                LocalDate minus = localDate.minusDays(localDate.getDayOfMonth());
                return orderRepository.findAllBy(pageable, minus, localDate,email);
            } else if (date.equalsIgnoreCase(Times.THIS_YEAR.toString())) {
                LocalDate localDate = LocalDate.now();
                LocalDate minus = localDate.minusDays(localDate.getDayOfYear());
                return orderRepository.findAllBy(pageable, minus, localDate,email);
            } else {
                return orderRepository.findAllBy(pageable,email);
            }
        }
        else{
            return Page.empty();
        }
    }



    @Override
    public Order getOrder(String email,Long orderId) {
            List<Order> byEmail = orderRepository.findByEmail(email);
            if (!byEmail.isEmpty()) {
                if (orderId != null) {
                    Order order = orderRepository.findByOrderIdEmail(orderId,email);
//                    OrderDTO  orderDTO=converToDTO(order);
//                    List<String> list=new ArrayList<>();
//                    List<String> res=new ArrayList<>();
//                    for (Product product : order.getProductsSet()) {
//                        list.add(product.getName());
//                        res.add(product.getReceiptNo());
//                    }
//                    orderDTO.setReceiptNumber(res);
//                    orderDTO.setMenu(list);
                    return order;
                }
            }
        return null;
    }


//alindi
    @Transactional
    @Override
    public boolean deleteOrder(String email,Long id) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
          Optional<Order> byOrderId = orderRepository.findByOrderId(id,email);

            if (byOrderId.isPresent()) {
                orderRepository.delete(byOrderId.get());
                System.out.println("DATA silindi");
                return true;
            }

               else{
                   return false;
               }
        }
        return false;
    }

    @Override
    public Long getTotalIncome(String email) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            LocalDate now = LocalDate.now();
            LocalDate today = now.minusMonths(1);
            List<Order> totalPrice = orderRepository.getTotalPrice(now, today, email);
            if(!totalPrice.isEmpty()) {
                Long total = 0l;
                for (Order order : totalPrice) {
                    for (Product product : order.getProductsSet()) {
                        total = total + product.getPrice() + product.getPrice() * product.getTax() - product.getDiscount() * product.getPrice();
                    }
                }
                return total;
            }

        }
        return null;
    }

    @Override
    public Order updateOrder(String email, Order order) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            Order byOrderId = orderRepository.findByOrderIdEmail(order.getOrderId(),email);
            if (byOrderId != null) {
                List<Product> list = order.getProductsSet().stream().toList();
                Long totalPrice = 0L;
                for (Product product : list) {
                    totalPrice=totalPrice+product.getPrice()+ product.getPrice() * product.getTax()-product.getPrice()*product.getDiscount() ;
                }
                order.setTotalPrice(totalPrice);
                byOrderId.setTotalPrice(order.getTotalPrice());
                byOrderId.setProductsSet(order.getProductsSet());
                byOrderId.setOrderDate(LocalDate.now());
                byOrderId.setPaymentMethod(order.getPaymentMethod());
               return byOrderId;
            }else{
                return null;
            }
        }else{
            return null;
        }

    }

    @Override
    public Order updateOrderCancel(String email, Order order) {
       try {
           List<Order> byEmail = orderRepository.findByEmail(email);
           if (!byEmail.isEmpty()) {
               Order byOrderId = orderRepository.findByOrderIdEmail(order.getOrderId(),email);
               if (byOrderId != null) {
                   return byOrderId;
               }
           }
       }
            catch (OurException e) {
            System.out.println("Our Exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       return null;
    }

    @Override
    public Integer countOrderByEmail(String email) {
        Integer i = orderRepository.countOrderByEmail(email);
        if(i>0){
            return i;
        }else{
            return 0;
        }
    }

    @Override
    public Long getTotalTips(String email) {
        List<Order> byEmail = orderRepository.findByEmail(email);
        if (!byEmail.isEmpty()) {
            LocalDate now = LocalDate.now();
            LocalDate today = now.minusMonths(1);
            List<Order> totalPrice = orderRepository.getTotalPrice(now, today, email);
            if(!totalPrice.isEmpty()) {
                Long total = 0l;
                for (Order order : totalPrice) {
                    for (Product product : order.getProductsSet()) {
                        total = total +product.getPrice() * product.getTax();
                    }
                }
                return total;
            }

        }
        return null;
    }

}
