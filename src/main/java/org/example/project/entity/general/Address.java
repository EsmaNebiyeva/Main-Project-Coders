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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String street;
    private String flat;
    private String streetNumber;
    private String postalCode;
    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserDetail userDetails;
    public Address(UserDetail user){
        this.userDetails=user;
    }
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", flat='" + flat + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", userDetails=" + userDetails +
                '}';
    }

    //    public Address(String city){
//        this.city = city;
//    }
}
