package org.example.project.entity.general;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.security.user.UserDetail;

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
    private String imageUrl;
    @OneToOne(fetch = FetchType.LAZY)
    //(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserDetail user;

    public BusinessDetails(UserDetail user){
        this.user=user;
    }


    @Override
    public String toString() {
        return "BusinessDetails{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", number='" + number + '\'' +
                ", businessEmail='" + businessEmail + '\'' +
                ", fax='" + fax + '\'' +
                ", user=" + user +
                '}';
    }
}
