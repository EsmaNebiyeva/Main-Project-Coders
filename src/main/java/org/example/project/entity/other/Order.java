package org.example.project.entity.other;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.security.user.UserDetail;

import java.math.BigDecimal;
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
    @Column(unique = true)
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserDetail user;
    private String userName;
    private String place;
    private String tables;
    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_products", // Ara tablonun ismi
            joinColumns = @JoinColumn(name = "order_id",referencedColumnName = "orderId"), // Öğrenciye referans
            inverseJoinColumns = @JoinColumn(name = "products_id", referencedColumnName = "receiptNo") // Derse referans
    )
    private List<Product> productsSet;
    private LocalDate orderDate= LocalDate.now();
private Long  totalPrice;
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
                ", cashier=" + user +
                ", products=" + productsSet +
                ", orderDate=" + orderDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
