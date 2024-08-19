package org.example.project.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;
import org.example.project.entity.Category;
import org.example.project.entity.Order;
import org.example.project.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@RequiredArgsConstructor

public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String receiptNo;
    private Long order;
    private Category category;
    private String imageUrl;
    private BigDecimal price;
    private Long stock;
    private Long tax;
   private Long discount;
   private Set<Order> ordersSet;
public  static ProductDTO convertToDto(Product product) {
   ProductDTO productDTO = new ProductDTO();
   productDTO.setId(product.getId());
   productDTO.setName(product.getName());
   productDTO.setReceiptNo(product.getReceiptNo());
   productDTO.setCategory(product.getCategory());
    productDTO.setStock(productDTO.getStock());
//    productDTO.setOrdersSet(product.getOrdersSet());
    productDTO.setImageUrl(productDTO.getImageUrl());
    productDTO.setPrice(productDTO.getPrice());
    productDTO.setTax(product.getTax());
    productDTO.setDiscount(product.getDiscount());
    return productDTO;
}


    public static Product fromDTOToNormal(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setTax(dto.getTax());
        product.setDiscount(dto.getDiscount());
        product.setCategory(dto.getCategory());
        product.setReceiptNo(dto.getReceiptNo());
        product.setTax(dto.getTax());
        product.setDiscount(dto.getDiscount());
        return product;

}

}
