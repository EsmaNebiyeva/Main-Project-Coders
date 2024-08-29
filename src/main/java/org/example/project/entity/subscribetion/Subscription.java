package org.example.project.entity.subscribetion;

import jakarta.persistence.*;
import lombok.Data;
import org.example.project.entity.other.User;

import java.time.LocalDate;

@Entity
@Data
@Table(name="subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    // Getters and Setters
}