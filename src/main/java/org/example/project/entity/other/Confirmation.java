package org.example.project.entity.other;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.security.user.UserDetail;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserDetail user;
    private String randomPassword;
    private LocalDateTime expireDate=LocalDateTime.now().plusMinutes(30);

//    public Confirmation(UserDetail user, String randomPassword) {
//        this.user= user;
//        this.randomPassword = randomPassword;
//    }

    public Confirmation(UserDetail email, String randomPassword) {
        this.randomPassword = randomPassword;
        this.user = email;
    }

    @Override
    public String toString() {
        return "Confirmation{" +
                "id=" + id +
                ", user=" + user +
                ", randomPassword='" + randomPassword + '\'' +
                '}';
    }
}
