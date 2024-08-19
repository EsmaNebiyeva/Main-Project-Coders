package org.example.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User cashier;
    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders_products", // Ara tablonun ismi
            joinColumns = @JoinColumn(name = "orderId"), // Öğrenciye referans
            inverseJoinColumns = @JoinColumn(name = "products_receiptNo") // Derse referans
    )
    private Set<Product> productsSet=new HashSet<>();
    private LocalDate orderDate= LocalDate.now();

    private String paymentMethod;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
              ", orderId='" + orderId +
                ", cashier=" + cashier +
                ", products=" + productsSet +
                ", orderDate=" + orderDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
