package org.example.project.entity.other;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.security.user.UserDetail;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
public class Account {


    public Account() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private Date birthDate;
    private String gender;
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email",referencedColumnName = "email")
private UserDetail userDetail;
}
