package org.example.project.service.other;

import java.time.LocalDateTime;
import java.util.List;

import org.example.project.entity.other.Order;
import org.springframework.data.domain.Page;



public interface OrderService {
    Order addOrder(Order order) ;
    Page<Order> getOrders(String email,Integer page, Integer size, String date,String filter);
    Order getOrder(String email,Long orderId);
    boolean deleteOrder(String email,Long orderId);
    Double getTotalIncome(String email);
    Order updateOrder(String email,Order order);
    Order updateOrderCancel(String email,Order order);
    //Integer countOrderByEmail(String email);
    Double getTotalTips(String email);
    Double getAll(String email);
    List<String> getCashiers(String email);
    List<Order> getOrderByNameSpecial(Long name,String email);
    Double getIncomeByMonth(String year,String month,String email);
   List<Double> getIncomeByDay(String year,String month,String email);
    Integer getOrderCount(String email,Integer page, Integer size, String date,String filter);
   List<String> getYears(String email);
   Double getIncomeByYear(String year,String email);
   List<Order> findByDate(LocalDateTime now);
}
