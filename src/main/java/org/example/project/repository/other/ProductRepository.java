package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product  p where upper( p.name)=upper(:name)")
    List<Product> findByName(String name);
    @Query("SELECT p FROM Product  p where upper(p.receiptNo)=upper(:receiptNo)")
    List<Product> findByReceiptNo(String receiptNo);
    @Query("select p from  Product  p where p.id=:id")
    List<Product> findAllById(Long id);
}
