package org.example.project.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class OrderProduct {
    private Order order;
    private Set<Product> productList=new HashSet<>();
}
