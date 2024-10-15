//package org.example.project.entity.other;
//
//import jakarta.persistence.*;
//
//public class Roles {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private User user;
//}
