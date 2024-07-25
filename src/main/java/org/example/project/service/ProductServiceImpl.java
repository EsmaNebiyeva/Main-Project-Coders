package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Product;
import org.example.project.exception.OurException;
import org.example.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
        System.out.println("DATA elave olundu");

    }
    @Transactional
    @Override
    public boolean deleteProduct(Product product) {
        try {
            if (productRepository.existsById(product.getId())) {
                productRepository.deleteById(product.getId());
            }
        }
        catch (OurException e) {
            System.out.println("Our Exception");
            System.out.println(e.getMessage());
            return false;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    @Transactional
    @Override
    public boolean deleteProductById(Long id) {
        try {
            if (productRepository.existsById(id)){
                productRepository.deleteById(id);
            }
        }
        catch (OurException e) {
            System.out.println("Our Exception");
            System.out.println(e.getMessage());
            return false;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public Product updateProduct(Long id,Product product) {
       try{
           Optional<Product> ignored =  productRepository.findById(id);
           if(ignored.isPresent()){
               ignored.get().setName(product.getName());
               ignored.get().setPrice(product.getPrice());
               ignored.get().setReceiptNo(product.getReceiptNo());
               return ignored.get();
           }

       } catch (OurException e) {
           System.out.println("Our Exception");
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
       }

        return product;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
       return productRepository.findById(id);
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
    public Product updateProductWithCancel(Long id,Product product) {
        try{
            Optional<Product> ignored =  productRepository.findById(id);
            if(ignored.isPresent()){
                ignored.get().setName(productRepository.findById(id).get().getName());
                ignored.get().setPrice(productRepository.findById(id).get().getPrice());
                ignored.get().setReceiptNo(productRepository.findById(id).get().getReceiptNo());
                return ignored.get();
            }

        } catch (OurException e) {
            System.out.println("Our Exception");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return product;
    }

}
