package org.example.project.entity.subscribetion;

import jakarta.persistence.*;
import lombok.Data;

import org.example.project.security.user.UserDetail;

import java.time.LocalDate;

@Entity
@Data
@Table(name="subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String subscriptionId;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserDetail user;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "plan_id",referencedColumnName = "name")
    private Plan plan;
    private LocalDate startDate=LocalDate.now();
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    // Getters and Setters
}