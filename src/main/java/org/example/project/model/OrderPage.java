package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderPage {
    private List<OrderDTO> orders;
    private int countOrders;
}