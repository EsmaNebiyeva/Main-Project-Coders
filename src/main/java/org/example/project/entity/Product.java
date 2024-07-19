package org.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String receiptNo;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Order> order;
    private BigDecimal price;
    private Long stock;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private Long tax;
    private Long discount;

    public Product(String name) {
        this.name = name;
    }
}
