package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Category;
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
    @Query("SELECT p FROM Product  p where upper( p.name) like upper(concat(:name,'%')) and upper(p.user.email)=upper(:email)")
    List<Product> findByName(String name,String email);
    @Query("SELECT p FROM Product  p where upper(p.receiptNo)=upper(:receiptNo)")
    Product findByReceiptNo(String receiptNo);//don't use
    @Query("SELECT p FROM Product  p where upper(p.receiptNo)=upper(:receiptNo) ")
    Product findByReceiptNoAndEmail(String receiptNo);
    @Query("SELECT p FROM Product  p where upper(p.receiptNo)=upper(:receiptNo) and upper(p.user.email)=upper(:email)")
    Product findByReceiptNoAndEmail(String receiptNo,String email);
    @Query("select p from  Product  p where p.id=:id")
    List<Product> findAllById(Long id);
    @Query("select p from Product p where p.receiptNo in (select distinct a.receiptNo from Product a)")
    Page<Product>  findAllDistinct(Pageable pageable);//don't use
    @Query("select count(distinct p.name) from Product  p ")
    Long findDistinctProductCount();
    @Query ("select  sum(p.stock)  from Product p")
    BigDecimal findSumStock();
    @Query("select count(distinct p.category) from Product p")
    Long findDistinctCategoryCount();
    @Query("select p from Product p where upper(p.user.email)=upper(:email)")
    Page<Product> findByEmail(String email,Pageable pageable);
    @Query("select count(p) from Product p where upper(p.user.email)=upper(:email)")
    Integer countByEmail(String email);
    @Query("SELECT COUNT(DISTINCT o) FROM Order o JOIN o.productsSet p WHERE upper(p.name) = upper(:name) and o.orderDate between :date and :now ")
    Integer findCountNameByOrdersSet(String name, LocalDate date ,LocalDate now);
    @Query("SELECT COUNT(DISTINCT o) FROM Order o JOIN o.productsSet p WHERE upper(p.name) = upper(:name) and o.orderDate between :date and :now and upper(p.user.email)=upper(:email)")
    Integer findCountNameByOrdersSetEmail(String name, LocalDate date ,LocalDate now, String email);
    @Query("select p from Product p where upper(p.category.name)=upper(:category) ")
    List<Product>  getProductsByCategory(String category);//don't use
    @Query("select p from Product p where upper(p.user.email)=upper(:email)")
    List<Product> findByEmail(String email);
    void deleteByReceiptNo(String no);
    @Query("select count(p) from Product p where upper(p.category.name)=upper(:name) and upper(p.user.email)=upper(:email)")
    int findCountCategoryByName(String email,String name);
    @Query("select c from Category c where upper(c.name)=upper(:name)")
      Category findByName(String name);
//    @Query("select distinct (p.category.name) from Product p where p.category.name=:name and p.category.userDetail.email=:email")
//    String  findCategoryByName(String email,String name);

}
