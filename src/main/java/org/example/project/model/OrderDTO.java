package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Order;
import org.example.project.entity.Product;
import org.example.project.entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDTO {

   // private Long id;
    private String orderId;

    private User cashier;

    private Set<Product> productsSet;

    private LocalDate orderDate=LocalDate.now();

    private String paymentMethod;


}
