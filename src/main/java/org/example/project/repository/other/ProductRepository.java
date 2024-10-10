package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product  p where upper( p.name) like upper(concat(:name,'%'))")
    List<Product> findByName(@Param("name")String name);
    @Query("SELECT p FROM Product  p where upper(p.receiptNo)=upper(:receiptNo)")
    List<Product> findByReceiptNo(String receiptNo);
    @Query("select p from  Product  p where p.id=:id")
    List<Product> findAllById(Long id);
    @Query("select p from Product p where p.receiptNo in (select distinct a.receiptNo from Product a)")
    Page<Product>  findAllDistinct(Pageable pageable);
    @Query("select count(distinct p.name) from Product  p ")
    Long findDistinctProductCount();
    @Query ("select  sum(p.stock)  from Product p")
    BigDecimal findSumStock();
    @Query("select count(distinct p.category) from Product p")
    Long findDistinctCategoryCount();
}
