//package org.example.project.controller;
//
//import lombok.RequiredArgsConstructor;
//
//import org.example.project.entity.other.Order;
//
//import org.example.project.exception.OurException;
//import org.example.project.model.OrderDTO;
//
//import org.example.project.service.other.OrderService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/api/order")
//@RequiredArgsConstructor
//public class OrderController {
//    @Autowired
//   private final OrderService orderService;
//    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
//    @GetMapping("/getById")
//    public ResponseEntity<Order> getOrder(@RequestParam Long id ) {
//        try{
//        Order order = orderService.getOrder(id);
//        return new ResponseEntity<>(order,HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//    @GetMapping("/get")
//    public ResponseEntity<List<Order>> getAllOrder(@RequestParam int page, @RequestParam int size,@RequestParam String date) {
//      try{  List<Order> orderList= orderService.getOrders(page,size,date).stream().toList();
//        return new ResponseEntity<>(orderList, HttpStatus.OK);
//    }
//        catch(Exception e){
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//    }
////    @PostMapping("/add")
////    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
////       try {
////           System.out.println("Brle");
////           Order order = orderService.addOrder(orderDTO);
////           return new ResponseEntity<>(order, HttpStatus.OK);
////       }
////       catch(Exception e){
////           System.out.println("PIs");
////           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////
////       }
////    }
//@PostMapping("/add")
//public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
//    try {
//        logger.info("Attempting to add order");
//        Order order = orderService.addOrder(orderDTO);
//        return new ResponseEntity<>(order, HttpStatus.CREATED);  // 201 Created for successful creation
//    } catch (OurException e) {
//        logger.error("Error adding order: {}", e.getMessage());
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Bad request for validation errors
//    } catch (Exception e) {
//        logger.error("Unexpected error: {}", e.getMessage());
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Internal server error for unexpected issues
//    }
//}
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteOrders(@RequestParam  Long id) {
//       try{
//            orderService.deleteOrder(id);
//            return new ResponseEntity<>("Data Silindi", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Silinmedi"+e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//}
package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;
import org.example.project.exception.OurException;
import org.example.project.model.OrderDTO;
import org.example.project.model.OrderPage;
import org.example.project.security.config.JwtService;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
import org.example.project.service.other.OrderService;
import org.example.project.service.other.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.project.model.OrderDTO.converToDTO;
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private final OrderService orderService;
    @Autowired
    private final UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ProductService productService;

    // Get order by ID
    @GetMapping("/getById")
    public ResponseEntity<OrderDTO> getOrder(HttpServletRequest request, @RequestParam Long id) {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Order order = orderService.getOrder(email, id);
                if (order == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }else{
                    OrderDTO orderDTO = converToDTO(order);
                    List<String> list = new ArrayList<>();
                    List<String> res = new ArrayList<>();
                    List<Product> list1 = order.getProductsSet().stream().toList();
                    Long total= 0l;
                    for (Product product : list1) {
                        list.add(product.getName());
                        res.add(product.getReceiptNo());
                        total=total+product.getPrice()+product.getPrice()*product.getTax()-product.getDiscount()*product.getPrice();
                    }
                    orderDTO.setReceiptNumber(res);
                    orderDTO.setMenu(list);
                    orderDTO.setPrice(total);
                return new ResponseEntity<>(orderDTO, HttpStatus.OK);
            }}

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Get all orders with pagination and date filter
    @GetMapping("/get")
    public ResponseEntity<OrderPage> getAllOrders(HttpServletRequest request, @RequestParam int page, @RequestParam int size, @RequestParam String date) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);

                List<Order> orderList = orderService.getOrders(email, page, size, date).stream().toList();
                Integer i = orderService.countOrderByEmail(email);
                List<OrderDTO> orderDTOs = new ArrayList<>();

                for (Order order : orderList) {
                    OrderDTO orderDTO = converToDTO(order);  // Assuming this converts order to DTO correctly
                    List<String> list = new ArrayList<>();
                    List<String> res = new ArrayList<>();
                    Long total=0l;
                    for (Product product : order.getProductsSet()) {
                        list.add(product.getName());
                        res.add(product.getReceiptNo());
                        total=total+product.getPrice()+product.getPrice()*product.getTax()-product.getDiscount()*product.getPrice();
                    }
                    orderDTO.setReceiptNumber(res);
                    orderDTO.setMenu(list);
                    orderDTO.setPrice(total);
                    orderDTOs.add(orderDTO);
                }
                OrderPage orderPage = new OrderPage();
                orderPage.setOrders(orderDTOs);
                orderPage.setCountOrders(i);

                return new ResponseEntity<>(orderPage, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting orders: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Add a new order
    @PostMapping("/add")
    public ResponseEntity<String> addOrder(HttpServletRequest request, @RequestBody Order order) {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);

                // Create order and associate with user
                UserDetail user = userService.findByEmail(email);
                if (user != null) {
                    order.setUser(user);
                    orderService.addOrder(order);
                    return new ResponseEntity<>("Order added successfully", HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

    }

    // Delete an order
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(HttpServletRequest request, @RequestParam Long id) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                boolean deleted = orderService.deleteOrder(email, id);
                if (deleted) {
                    return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception e) {
            logger.error("Error deleting order: {}", e.getMessage());
            return new ResponseEntity<>("Failed to delete order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Order not found", HttpStatus.UNAUTHORIZED);
    }

    // Get total income from orders
    @GetMapping("/income")
    public ResponseEntity<Long> getIncome(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Long income = orderService.getTotalIncome(email);
                return new ResponseEntity<>(income, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting income: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/tip")
    public ResponseEntity<Long> getTips( HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Long income = orderService.getTotalTips(email);
                return new ResponseEntity<>(income, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting income: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // Update an order
    @PutMapping("/update")
    public ResponseEntity<OrderDTO> updateOrder(HttpServletRequest request, @RequestBody Order order) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Order updatedOrder = orderService.updateOrder(email, order);
                if (updatedOrder != null) {
                    OrderDTO orderDTO = converToDTO(updatedOrder);
                    List<String> list = new ArrayList<>();
                    List<String> res = new ArrayList<>();
                    for (Product product : updatedOrder.getProductsSet()) {
                        list.add(product.getName());
                        res.add(product.getReceiptNo());
                    }
                    orderDTO.setReceiptNumber(res);
                    orderDTO.setMenu(list);
                    return new ResponseEntity<>(orderDTO, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            logger.error("Error updating order: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Cancel an order
    @PutMapping("/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(HttpServletRequest request, @RequestBody Order order) {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtService.extractEmail(token);
                Order canceledOrder = orderService.updateOrderCancel(email, order);
                if (canceledOrder != null) {
                    OrderDTO orderDTO = converToDTO(canceledOrder);
                    List<String> list = new ArrayList<>();
                    List<String> res = new ArrayList<>();
                    for (Product product : canceledOrder.getProductsSet()) {
                        list.add(product.getName());
                        res.add(product.getReceiptNo());
                    }
                    orderDTO.setReceiptNumber(res);
                    orderDTO.setMenu(list);
                    return new ResponseEntity<>(orderDTO, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            logger.error("Error canceling order: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
