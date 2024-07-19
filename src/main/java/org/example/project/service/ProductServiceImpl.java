package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Product;
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
    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
        System.out.println("DATA elave olundu");

    }

    @Override
    public void deleteProduct(Product product) {
    if(productRepository.existsById(product.getId())) {
        productRepository.deleteById(product.getId());
        System.out.println("Silindi");
    }
        System.out.println("Silinmedi");
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
        System.out.println("DATA elave olundu");
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
}
