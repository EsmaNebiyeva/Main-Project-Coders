package org.example.project.entity.notification;


import java.util.List;

import org.example.project.model.MoreActivitiesEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MoreActivity {
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String email;
    @Enumerated(EnumType.STRING)
    private List<MoreActivitiesEnum> notList;
    
}
