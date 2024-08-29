package org.example.project.entity.other;

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
//    @Column(unique = true)
//    private String orderId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User cashier;
    @ManyToMany( fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinTable(
            name = "orders_products", // Ara tablonun ismi
            joinColumns = @JoinColumn(name = "order_id"), // Öğrenciye referans
            inverseJoinColumns = @JoinColumn(name = "products_id") // Derse referans
    )
    private Set<Product> productsSet=new HashSet<>();
    private LocalDate orderDate= LocalDate.now();

    private String paymentMethod;
//    public void addCourse(Product order) {
//        this.productsSet.add(order);
//        order.getOrdersSet().add(this);
//    }
//
//    public void removeCourse(Product order) {
//        this.productsSet.remove(order);
//        order.getOrdersSet().remove(this);
//    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
//              ", orderId='" + orderId +
                ", cashier=" + cashier +
                ", products=" + productsSet +
                ", orderDate=" + orderDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
