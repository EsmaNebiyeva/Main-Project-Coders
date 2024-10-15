package org.example.project.entity.other;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.RequiredArgsConstructor;
import org.example.project.security.user.UserDetail;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String receiptNo;
    private String imageUrl;
    private Long price;
    @ManyToOne(fetch = FetchType.LAZY )//,cascade = CascadeType.REMOVE
    @JoinColumn(name = "category_id",referencedColumnName = "name")
    private Category category;
    private Long stock;
    @ManyToOne(fetch = FetchType.LAZY)//,cascade = CascadeType.REMOVE
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserDetail user;
//qetiyyen acilmir
//    @ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "productsSet")
//
//    private Set<Order> ordersSet=new HashSet<>();
    private Long tax;
    private Long discount;

//    public Set<Order> getOrdersSet() {
//       ordersSet=new HashSet<>();
//    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", receiptNo='" + receiptNo + '\'' +
                ", price=" + price +
//                ", stock=" + stock +
//                ",imageUrl='" +imageUrl + '\'' +
//                ", user=" + user +
                ", tax=" + tax +
                ", discount=" + discount +
                '}';
    }

    public Product( String name) {
        this.name = name;
    }
}
