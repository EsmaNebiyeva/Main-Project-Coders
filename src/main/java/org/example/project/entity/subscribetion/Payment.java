//package org.example.project.entity.subscribetion;
//
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Entity
//@Data
//@Table(name="payments")
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "subscription_id")
//    private Subscription subscription;
//
//    private LocalDate paymentDate;
//    private Double amount;
//    private String paymentMethod; // "credit_card", "paypal" vb.

    // Getters and Setters
//}