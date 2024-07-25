package org.example.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "users")
public  class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "this is required")
    private String username;
    @NotBlank(message = "this is required")
    @Email(message = "Email is required")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "this is required")
    @NotNull
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    @Column(unique = true)
    private String phoneNumber;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private String role;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products ;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders ;

    private Date birthDate;
    private Date signUpDate;
    private String accessType;
    private String gender;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;


    public User(Long id, String username, String email, String phoneNumber, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", birthDate=" + birthDate +
                ", signUpDate=" + signUpDate +
                ", accessType='" + accessType + '\'' +
                ", gender='" + gender + '\'' +
                ", address=" + address +
                '}';
    }
}
