package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Category;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;

import org.example.project.security.user.UserDetail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.thymeleaf.util.StringUtils.concat;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private List<String> receiptNumber;
    private String cashier;
    private List<String> menu;
private Long price;


    private LocalDate orderDate;
 //private List<String> productsName=new ArrayList<>();
    private String paymentMethod;

    public static OrderDTO converToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.orderId = order.getOrderId();
        dto.cashier = concat(order.getUser().getFirstname()+" ", order.getUser().getLastname());
        dto.orderDate=order.getOrderDate();
        dto.paymentMethod = order.getPaymentMethod();
        dto.price=order.getTotalPrice();

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
