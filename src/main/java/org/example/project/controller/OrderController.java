package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.Order;
import org.example.project.entity.Product;
import org.example.project.service.OrderService;
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
    @Autowired
    private final OrderService orderService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id ) {
        try{
        Order order = orderService.getOrder(id);
        return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get/{page}/{size}")
    public ResponseEntity<List<Order>> getAllOrder(@PathVariable int page, @PathVariable int size) {
      try{  List<Order> orderList= orderService.getOrders(page,size).getContent();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
        catch(Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    }
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
       try{ orderService.addOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")

    public ResponseEntity<String> deleteOrders(@PathVariable Long id) {
       //try{
            orderService.deleteOrder(id);
            return new ResponseEntity<>("Data Silindi", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Silinmedi",HttpStatus.BAD_REQUEST);
//        }
    }
}
