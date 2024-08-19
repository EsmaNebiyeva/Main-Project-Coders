package org.example.project.service;

import org.example.project.entity.Product;
import org.example.project.model.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(Product product);
    boolean deleteProduct(Product product);
    boolean deleteProductById(Long id);
    Product updateProduct(Long id,Product product);
    ProductDTO getProductById(Long id);
    Page<Product> getProducts(Integer page, Integer size);
    List<Product> getProducts();
    Product updateProductWithCancel(Long id, Product product);
    List<Product> getProductByName(String name);
    Boolean existsProductByReceiptNo(String receiptNo);

}
