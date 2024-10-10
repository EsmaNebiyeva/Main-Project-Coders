package org.example.project.entity.subscribetion;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String duration;

    // Getters and Setters
}