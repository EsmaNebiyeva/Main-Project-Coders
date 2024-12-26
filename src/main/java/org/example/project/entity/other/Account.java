package org.example.project.entity.other;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.example.project.security.user.UserDetail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account {


    public Account() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String password;
    private String phone;
    private String email;
    private Date birthDate;
    private String gender;
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email",referencedColumnName = "email")
   private UserDetail userDetail;
    @Column(nullable = true)
    private LocalDate deletedDate;
public Account(String name, String password, String phone, String email, Date birthDate, String gender, String image) {
    this.name = name;
    this.password = password;
    this.phone = phone;
    this.email = email;
    this.birthDate = birthDate;
    this.gender = gender;
    this.image = image;
}
public Account(String name, String password, String phone, String email) {
    this.name = name;
    this.password = password;
    this.phone = phone;
    this.email = email;
}
}
