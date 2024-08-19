package org.example.project.controller;

import lombok.RequiredArgsConstructor;

import org.example.project.entity.Order;

import org.example.project.model.OrderDTO;

import org.example.project.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
   private final OrderService orderService;

    @GetMapping("/getById")
    public ResponseEntity<Order> getOrder(@RequestParam Long id ) {
        try{
        Order order = orderService.getOrder(id);
        return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get")
    public ResponseEntity<List<Order>> getAllOrder(@RequestParam int page, @RequestParam int size,@RequestParam String date) {
      try{  List<Order> orderList= orderService.getOrders(page,size,date).stream().toList();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
        catch(Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    }
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
       try{

           Order order = orderService.addOrder(orderDTO);
           return new ResponseEntity<>(order, HttpStatus.OK);
    }
        catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrders(@RequestParam String orderId) {
       try{
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>("Data Silindi", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Silinmedi",HttpStatus.BAD_REQUEST);
        }
    }
}
