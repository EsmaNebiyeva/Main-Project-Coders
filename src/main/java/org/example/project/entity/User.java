package org.example.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.util.Collection;
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
    @OneToMany
    private List<Product> products ;
    @OneToMany
    private List<Order> orders ;

    private Date birthDate;

    private String gender;
    @ManyToOne
    private Address address;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role));
//    }

    public User(Long id, String username, String email, String phoneNumber, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
