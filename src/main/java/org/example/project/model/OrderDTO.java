package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Date;
import java.util.List;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private String orderId;

    private UserDTO cashier;

    private List<ProductDTO> productsOrder;

    private Date orderDate;

    private String paymentMethod;


}
