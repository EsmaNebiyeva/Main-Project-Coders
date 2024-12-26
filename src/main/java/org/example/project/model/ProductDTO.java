package org.example.project.model;



import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Category;

import org.example.project.entity.other.Product;




@Data
@RequiredArgsConstructor

public class ProductDTO {
    private Long id;
    private String name;
    private String receiptNo;
    private Integer orderofDay;
    private String category;
    private String imageUrl;
    private Double price;
    private Double stock;
    private Long tax;
    private Long discount;
    private String description;
public  static ProductDTO convertToDto(Product product) {
   ProductDTO productDTO = new ProductDTO();
   productDTO.setId(product.getId());
   productDTO.setName(product.getName());
   productDTO.setReceiptNo(product.getReceiptNo());
   productDTO.setCategory(product.getCategory().getName());
    productDTO.setStock(product.getStock());
    productDTO.setImageUrl(product.getImageUrl());
    productDTO.setPrice(product.getPrice());
    productDTO.setTax(product.getTax());
    productDTO.setDiscount(product.getDiscount());
    productDTO.setDescription(product.getDescription());
    return productDTO;
}


    public static Product fromDTOToNormal(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDiscount(dto.getDiscount());
        product.setCategory(new Category(dto.getCategory()));
        product.setReceiptNo(dto.getReceiptNo());
        product.setTax(dto.getTax());
        product.setDiscount(dto.getDiscount());
        product.setDescription(dto.getDescription());
        return product;

}

}
