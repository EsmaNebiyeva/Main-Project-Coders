package org.example.project.entity.other;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


import org.example.project.security.user.UserDetail;




@Entity
@Data
@AllArgsConstructor
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String receiptNo;
    private String imageUrl;
    private Double price;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER )//,cascade = CascadeType.REMOVE
    @JoinColumn(name = "category_id",referencedColumnName = "name")
    private Category category;
    private Double stock;
    private Double size;
    @ManyToOne(fetch = FetchType.EAGER)//,cascade = CascadeType.REMOVE
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
public Product() {
    this.receiptNo = generateReceiptNo();
}

private String generateReceiptNo() {
    long timestamp = System.currentTimeMillis();  // Millisaniye cinsinden zaman damgası
    int random = (int) (Math.random() * 0.0001);    // Benzersiz bir değer için rastgele bir sayı
    return timestamp+""+random;               // Zaman ve rastgele sayıyı birleştiriyoruz
}
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
                ", description=" + description +
                '}';
    }

    public Product( String name) {
        this.name = name;
    }
}
