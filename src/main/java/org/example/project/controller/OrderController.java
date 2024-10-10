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
import org.example.project.exception.OurException;
import org.example.project.model.OrderDTO;
import org.example.project.service.other.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private final OrderService orderService;

    // ici cixmir
    @GetMapping("/getById")
    public ResponseEntity<Order> getOrder(@RequestParam Long id) {
        try {
            Order order = orderService.getOrder(id);
            if (order == null) {
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting order by ID: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //alindi amma productset cixmir
    @GetMapping("/get")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam int page,
                                                    @RequestParam int size,
                                                    @RequestParam String date) {
        try {
            List<Order> orderList = orderService.getOrders(page, size, date).stream().toList();
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting orders: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//alindi
    // Add a new Order
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
        try {
//            logger.info("Attempting to add order");
            Order order = orderService.addOrder(orderDTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (OurException e) {
          //  logger.error("Error adding order: {}", e.getMessage());
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    //
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam Long id) {
        try {
            boolean deleted = orderService.deleteOrder(id);
            if (deleted) {
                return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //logger.error("Error deleting order: {}", e.getMessage());
            return new ResponseEntity<>("Failed to delete order", HttpStatus.BAD_REQUEST);
        }
    }
    //alindi
    @GetMapping("/income")
    public ResponseEntity<Long> getIncome() {
        try {
            Long income = orderService.getTotalIncome();
                return new ResponseEntity<>(income, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error deleting order: {}", e.getMessage());
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }
}
