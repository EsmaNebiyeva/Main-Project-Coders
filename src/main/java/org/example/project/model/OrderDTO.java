package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.example.project.entity.other.Order;

import org.example.project.repository.other.TablesRepository;



import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;



@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private List<String> receiptNumber;
    private String cashier;
    private List<String> menu;
    private List<ProductDTO> menus;
    private List<String> size;
    private Double price;
    private String place;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private List<String> tables;
  

    public static OrderDTO converToDTO(Order order,TablesRepository tablesRepository) {
        OrderDTO dto = new OrderDTO();
        dto.orderId = order.getOrderId();
        dto.cashier = order.getUserName();
        dto.orderDate=order.getOrderDate();
        dto.paymentMethod = order.getPaymentMethod();
        dto.price=order.getTotalPrice();
        dto.place=order.getPlace();

        List<String> tablessses=new ArrayList<>();
        List<String> table2 = order.getTables();

        if(table2!=null){
        for(String ad: table2){
        if(ad!=null){
            tablessses.add(ad);
        } }}
        dto.setTables(tablessses);
        return dto;
    }
    public static OrderDTO converToDTOs(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.orderId = order.getOrderId();
        dto.cashier = order.getUserName();
        dto.orderDate=order.getOrderDate();
        dto.paymentMethod = order.getPaymentMethod();
        dto.price=order.getTotalPrice();
        dto.place=order.getPlace();

        List<String> tablessses=new ArrayList<>();
        List<String> table2 = order.getTables();

        if(table2!=null){
        for(String ad: table2){
        if(ad!=null){
            tablessses.add(ad);
        } }}
        dto.setTables(tablessses);
        return dto;
    }
//
// public static Product fromDTOToNormal(ProductDTO dto) {
//  Product product = new Product();
//  product.setId(dto.getId());
//  product.setName(dto.getName());
//  product.setPrice(dto.getPrice());
//  product.setStock(dto.getStock());
//  product.setImageUrl(dto.getImageUrl());
//  product.setTax(dto.getTax());
//  product.setDiscount(dto.getDiscount());
//  product.setCategory(new Category(dto.getCategory()));
//  product.setReceiptNo(dto.getReceiptNo());
//  product.setTax(dto.getTax());
//  product.setDiscount(dto.getDiscount());
//  return product;
//
// }

}
