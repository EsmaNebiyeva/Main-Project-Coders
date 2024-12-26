package org.example.project.entity.subscribetion;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Data
@Table(name="subscriptions")

public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private Long subscriptionId=id;
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "user_email",referencedColumnName = "email")
    // private UserDetail user;
    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "plan_id",referencedColumnName = "name")
    private Plan plan;
    private LocalDate startDate=LocalDate.now();
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status status;
private String userEmail;
    // Getters and Setters
}