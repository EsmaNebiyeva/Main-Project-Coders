package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderPage {
    private List<OrderDTO> orders;
    private int countOrders;
    private Double sales;
    private List<String> cashiers;
}