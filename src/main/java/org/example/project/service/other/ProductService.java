package org.example.project.service.other;

import org.example.project.entity.other.Product;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);
    boolean deleteProduct(String product,String email);
    boolean deleteProductById(Long id);
    Product updateProduct(Product product,String email);
    Product getProductById(String product,String email);
    Page<Product> getProducts(Integer page, Integer size);
    List<Product> getProducts();
    Product updateProductWithCancel( Product product,String email);
    List<Product> getProductByName(String name);
    Boolean existsProductByReceiptNo(String receiptNo);
    Long getCategoryCount();
    Page<Product>  getProductByEmail(String email,Integer page,Integer size,String filter);
    Integer getProductOrderCount(String name);
    List<Product> getProductByCategory();
    List<Product > getProductByEmail();
   // Long countUserPermission(String email);
   Integer  countProductByEmail(String email);
    Integer getProductOrderCountEmail(String name,String email);
   List<Product> getProductByNameSpecial(String name,String email);
   Integer getProductCount();
   Integer getProductSize(String email);
}
