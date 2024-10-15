//package org.example.project.entity.other;
//
//import jakarta.persistence.ManyToOne;
//
//@Entity
//public class OrderProduct {
//
//    @EmbeddedId
//    private OrderProductId id;  // order_id, product_id
//
//    @ManyToOne
//    @MapsId("orderId")
//    private Order order;
//
//    @ManyToOne
//    @MapsId("productId")
//    private Product product;
//
//    @Column(nullable = false)
//    private int quantity;  // Sipariş edilen miktar
//
//    // Getter ve Setter metodları
//}
