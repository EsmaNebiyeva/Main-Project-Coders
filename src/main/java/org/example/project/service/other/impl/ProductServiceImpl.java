package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Product;
import org.example.project.exception.OurException;
import org.example.project.model.ProductDTO;
import org.example.project.repository.other.CategoryRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.service.other.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.project.model.ProductDTO.convertToDto;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
private final CategoryRepository categoryRepository;
private final OrderRepository orderRepository;
    @Transactional
    @Override
    public void addProduct(Product product) {
        if(product.getCategory() != null) {
            if(product.getCategory().getId() == null) {
               categoryRepository.save(product.getCategory());
            }
        }
        String image ="C:/Users/Asus/IdeaProjects/Project/src/main/resources/static/ " +product.getImageUrl();
        product.setImageUrl(image);
        productRepository.save(product);
        System.out.println("DATA elave olundu");
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
    public boolean deleteProductById(Long id) {
        try {
            if (productRepository.existsById(id)) {
                System.out.println("Delete");
                productRepository.deleteById(id);
            } else{
                System.out.println("Tapilmadi");
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
    public ProductDTO getProductById(Long id) {
        Product byId = productRepository.findById(id).get();
        ProductDTO productDTO=new ProductDTO();
       if(byId!=null) {
         productDTO = convertToDto(byId);
       }
       return productDTO;
    }

    @Override
    public Page<Product> getProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
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
