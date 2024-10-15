//package org.example.project.entity.other;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.example.project.entity.general.Address;
//import org.example.project.entity.subscribetion.Subscription;
//
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//@Entity
//@Data
//@RequiredArgsConstructor
//@AllArgsConstructor
//@Table(name = "users")
//public  class User  {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @NotBlank(message = "this is required")
//    private String username;
//   // @NotBlank(message = "this is required")
//    @Email(message = "Email is required")
//    @Column(unique = true)
//    private String email;
//    //@NotBlank(message = "this is required")
//    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
//    @Column(unique = true)
//    private String phoneNumber;
//   // @NotBlank(message = "Password is mandatory")
//    @Size(min = 6, message = "Password must be at least 6 characters")
//    private String password;
//    private String role;
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    //(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Product> products = new HashSet<>();
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    //(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Order> orders = new HashSet<>();
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Account> account = new HashSet<>();
//    private Date birthDate;
//    private LocalDateTime signUpDate = LocalDateTime.now();
//    private String accessType;
//    private String gender;
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Address address;
//    @OneToMany(fetch = FetchType.EAGER)
//    private Set<UserPermission> userPermission = new HashSet<>();
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Subscription> subscriptions = new HashSet<>();
//
//    public User(Long id, String username, String email, String phoneNumber, String password) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", email='" + email + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", password='" + password + '\'' +
//                ", role='" + role + '\'' +
//                ", birthDate=" + birthDate +
//                ", signUpDate=" + signUpDate +
//                ", accessType='" + accessType + '\'' +
//                ", gender='" + gender + '\'' +
//                ", address=" + address +
//                '}';
//    }
//
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() {
////        return List.of();
////    }
////
////    public String getPassword() {
////        return password;
////    }
////
////    @Override
////    public String getUsername() {
////        return email;
////    }
////
////    @Override
////    public boolean isAccountNonExpired() {
////        return true;
////    }
////
////    @Override
////    public boolean isAccountNonLocked() {
////        return true;
////    }
////
////    @Override
////    public boolean isCredentialsNonExpired() {
////        return true;
////    }
////
////    @Override
////    public boolean isEnabled() {
////        return true;
////    }
//}
//
