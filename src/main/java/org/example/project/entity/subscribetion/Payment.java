package org.example.project.entity.subscribetion;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@Table(name="payments")
@RequiredArgsConstructor
@AllArgsConstructor
public class Payment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String number;
   private String date;
   private String cvv;
   private Double amount;
   private String name;
}