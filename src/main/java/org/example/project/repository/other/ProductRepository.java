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
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product  p where upper( p.name) like upper(concat(:name,'%')) and p.user.email=:email")
    List<Product> findByName(String name,String email);
    @Query("SELECT p FROM Product  p where upper(p.receiptNo)=upper(:receiptNo)")
    Product findByReceiptNo(String receiptNo);//don't use
    @Query("SELECT p FROM Product  p where upper(p.receiptNo)=upper(:receiptNo) and p.user.email=:email")
    Product findByReceiptNoAndEmail(String receiptNo,String email);
    @Query("select p from  Product  p where p.id=:id")
    List<Product> findAllById(Long id);
    @Query("select p from Product p where p.receiptNo in (select distinct a.receiptNo from Product a)")
    Page<Product>  findAllDistinct(Pageable pageable);//don't use
    @Query("select count(distinct p.name) from Product  p ")
    Long findDistinctProductCount();
    @Query ("select  sum(p.stock)  from Product p")
    BigDecimal findSumStock();
    @Query("select count(distinct p.category) from Product p where p.user.email=:email")
    Long findDistinctCategoryCount(String email);
    @Query("select p from Product p where p.user.email=:email")
    Page<Product> findByEmail(String email,Pageable pageable);
    @Query("select count(p) from Product p where p.user.email=:email")
    Integer countByEmail(String email);
    @Query("SELECT COUNT(DISTINCT o) FROM Order o JOIN o.productsSet p WHERE p.name = :name and o.orderDate between :date and :now and p.user.email=:email")
    Integer findCountNameByOrdersSet(String name, LocalDate date ,LocalDate now,String email);
    @Query("select p from Product p where p.category.name=:category and p.user.email=:email")
    List<Product>  getProductsByCategory(String category,String email);//don't use
    @Query("select p from Product p where p.user.email=:email")
    List<Product> findByEmail(String email);
    void deleteByReceiptNo(String no);
    @Query("select count(p) from Product p where p.category.name=:name and p.user.email=:email")
    int findCountCategoryByName(String email,String name);
//    @Query("select distinct (p.category.name) from Product p where p.category.name=:name and p.category.userDetail.email=:email")
//    String  findCategoryByName(String email,String name);

}
