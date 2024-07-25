package org.example.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;
import org.example.project.entity.Order;
import org.example.project.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@RequiredArgsConstructor

public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String receiptNo;
    private List<OrderDTO> order;
    private String imageUrl;
      private BigDecimal price;
    private Long stock;
    private UserDTO user;
    private Long tax;
   private Long discount;
public  static ProductDTO convertToDto(Product product) {
   ProductDTO productDTO = new ProductDTO();
   productDTO.setId(product.getId());
   productDTO.setName(product.getName());
   productDTO.setReceiptNo(product.getReceiptNo());
    productDTO.setStock(productDTO.getStock());
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
        product.setReceiptNo(dto.getReceiptNo());
        product.setTax(dto.getTax());
        product.setDiscount(dto.getDiscount());
        return product;

}

}
