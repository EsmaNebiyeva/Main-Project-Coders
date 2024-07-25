package org.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;

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
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> order;
    private String imageUrl;
    private BigDecimal price;
    private Long stock;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   // @JoinColumn(name = "user_id")
    private User user;
    private Long tax;
    private Long discount;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", receiptNo='" + receiptNo + '\'' +
                ", order=" + order +
                ", price=" + price +
                ", stock=" + stock +
                ",imageUrl='" +imageUrl + '\'' +
                ", user=" + user +
                ", tax=" + tax +
                ", discount=" + discount +
                '}';
    }
}
