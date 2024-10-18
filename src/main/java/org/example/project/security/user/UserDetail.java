package org.example.project.security.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.entity.general.Address;
import org.example.project.entity.general.BusinessDetails;
import org.example.project.entity.other.*;
import org.example.project.entity.subscribetion.Subscription;
import org.example.project.security.token.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.concat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

public class UserDetail implements UserDetails {
    public UserDetail(String email) {
        this.email = email;
    }
//  @Id
//  @GeneratedValue
//  private Integer id;
//  private String firstname;
//  private String lastname;
//  private String phoneNumber;
//  @Email
//  @Column(unique = true)
//  private String email;
//  private String password;
//  @Enumerated(EnumType.STRING)
//  private Role role;
//
//  @OneToMany(mappedBy = "user")
//  private List<Token> tokens;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @NotBlank(message = "this is required")
  private String firstname;
  @NotBlank(message = "this is required")
  private String lastname;
  // @NotBlank(message = "this is required")
  @Email(message = "Mail is not valid")
  @Column(unique = true,nullable = false)
  private String email;
  private LocalDateTime created;
  //@NotBlank(message = "this is required")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",message = "Password is not valid")
  @Column(unique = true)
  private String phoneNumber;
  // @NotBlank(message = "Password is mandatory")
  @Size(min = 6, message = "Password must be at least 6 characters")
  private String password;
 // @OneToMany(fetch = FetchType.EAGER)
//  @JoinTable(
//          name = "user_roles",
//          joinColumns = @JoinColumn(name = "user_email"),
//          inverseJoinColumns = @JoinColumn(name = "role_id")
//  )
    @Enumerated(EnumType.STRING)
   private Role role;
//  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//  //(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  private Set<Product> products = new HashSet<>();
//  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//  //(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  private Set<Order> orders = new HashSet<>();
  @OneToMany(mappedBy = "userDetail", cascade = CascadeType.REMOVE)
  private Set<Account> account = new HashSet<>();
  @OneToOne(mappedBy = "userDetails",cascade = CascadeType.REMOVE)
  private Address address;
//  @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
//  private Set<Confirmation> confirmations = new HashSet<>();
  @OneToMany(fetch = FetchType.EAGER)
  private Set<UserPermission> userPermission = new HashSet<>();
//  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
//  private Set<Subscription> subscriptions = new HashSet<>();
  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  //(fetch = FetchType.EAGER)
  private BusinessDetails businessDetails;
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public String toString() {
    return "UserDetail{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", email='" + email + '\'' +
            ", created=" + created +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", password='" + password + '\'' +
            ", role=" + role +
           // ", products=" + products +
          //  ", orders=" + orders +
            ", account=" + account +
            ", address=" + address +
            ", userPermission=" + userPermission +
           // ", subscriptions=" + subscriptions +
            ", businessDetails=" + businessDetails +
            ", tokens=" + tokens +
            '}';
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
