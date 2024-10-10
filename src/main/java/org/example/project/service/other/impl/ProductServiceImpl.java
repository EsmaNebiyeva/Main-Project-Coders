package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Product;
import org.example.project.exception.OurException;

import org.example.project.repository.other.CategoryRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.service.other.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.concat;



@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
private final CategoryRepository categoryRepository;
private final OrderRepository orderRepository;
private final String imagePath="C:\\Users\\Asus\\IdeaProjects\\Project\\src\\main\\resources\\static";
    @Transactional
    @Override
    public void addProduct(Product product) {
     try {

            // if (categoryRepository.findByNameAndId(product.getCategory().getName(), product.getCategory().getId()) != null) {


                 System.out.println("DATA elave olundu");
                 productRepository.save(product);

           //  }

     }
     catch (Exception e) {
         System.out.println("XETA CATCH");
     }

    }

    @Transactional
    @Override
    public boolean deleteProduct(Product product) {
        try {
            if (productRepository.existsById(product.getId())) {
                productRepository.delete(product);
                System.out.println("Elendi");
                return true;
            }
        } catch (OurException e) {
            System.out.println("Our Exception");
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteProductById(Long id) throws OurException {

            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return true;  // Silmə əməliyyatı uğurlu oldu
            } else {

                return false;
            }

    }

//    @Transactional
//    @Override
//    public boolean deleteProductById(Long id) {
//        try {
//            if (productRepository.existsById(id)) {
//                System.out.println("Delete");
//                productRepository.deleteById(id);
//            } else{
//                System.out.println("Tapilmadi");
//            }
//        } catch (OurException e) {
//            System.out.println("Our Exception");
//            System.out.println(e.getMessage());
//            return false;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//        return true;
//    }

    @Transactional
    @Override
    public Product updateProduct(Long id, Product product) {
        try {
            Optional<Product> ignored = productRepository.findById(id);
            if (ignored.isPresent()) {
                ignored.get().setName(product.getName());
                ignored.get().setPrice(product.getPrice());
                ignored.get().setReceiptNo(product.getReceiptNo());
                //ignored.get().setOrdersSet(product.getOrdersSet());
                ignored.get().setStock(product.getStock());
                ignored.get().setTax(product.getTax());
                ignored.get().setDiscount(product.getDiscount());
                ignored.get().setCategory(product.getCategory());
                ignored.get().setImageUrl(product.getImageUrl());

                return ignored.get();
            }

        } catch (OurException e) {
            System.out.println("Our Exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return product;
    }

    @Override
    public Product getProductById(Long id) {
        Product byId = productRepository.findById(id).get();


       if(byId!=null) {

         byId.setImageUrl(concat(imagePath,byId.getImageUrl()));
       }
       return byId;
    }

    @Override
    public List<Product> getProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> allDistinct = productRepository.findAllDistinct(pageable).getContent();
        return allDistinct;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> all = productRepository.findAll();
        return all;
    }

    @Transactional
    @Override
    public Product updateProductWithCancel(Long id, Product product) {
        try {
            Optional<Product> ignored = productRepository.findById(id);
            if (ignored.isPresent()) {
                ignored.get().setName(ignored.get().getName());
                ignored.get().setPrice(ignored.get().getPrice());
                ignored.get().setReceiptNo(ignored.get().getReceiptNo());
                // ignored.get().setUser();
               // ignored.get().setOrdersSet(ignored.get().getOrdersSet());
                ignored.get().setStock(ignored.get().getStock());
                ignored.get().setTax(ignored.get().getTax());
                ignored.get().setDiscount(ignored.get().getDiscount());
                ignored.get().setCategory(ignored.get().getCategory());
                ignored.get().setImageUrl(ignored.get().getImageUrl());

               productRepository.save(ignored.get());
            }

        } catch (OurException e) {
            System.out.println("Our Exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return product;
    }

    @Override
    public List<Product> getProductByName(String name) {
            List<Product> products = productRepository.findByName(name);
            return products;
    }

    @Override
    public Boolean existsProductByReceiptNo(String receiptNo) {
        List<Product> byReceiptNo = productRepository.findByReceiptNo(receiptNo);
        if (!byReceiptNo.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
