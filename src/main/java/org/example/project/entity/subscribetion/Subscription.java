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

    @ManyToOne
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserDetail user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    // Getters and Setters
}