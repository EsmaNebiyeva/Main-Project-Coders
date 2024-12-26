package org.example.project.entity.notification;

import java.util.List;

import org.example.project.model.EmailNots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "email_nots")
public class EmailNot {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private List<EmailNots> notifList;
    
}
