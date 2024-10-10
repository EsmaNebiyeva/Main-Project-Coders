package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Product;
import org.example.project.entity.other.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDTO {

   // private Long id;
    private Long orderId;

    private User cashier;

    private Set<Product> productsSet;

    private LocalDate orderDate=LocalDate.now();

    private String paymentMethod;
    private BigDecimal totalAmount;


}
