package org.example.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor

public class BusinessDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String storeName;
    private String number;
    @Column(unique = true)
    private String businessEmail;
    private String fax;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //(fetch = FetchType.EAGER)
    private User user;

    public BusinessDetails(String businessEmail){
        this.businessEmail = businessEmail;
    }
    @Override
    public String toString() {
        return "BusinessDetails{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", number='" + number + '\'' +
                ", businessEmail='" + businessEmail + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }
}
