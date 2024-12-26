package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Category;
import org.example.project.entity.other.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@EnableJpaRepositories
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product  p where upper( p.name) like upper(concat(:name,'%')) and upper(p.user.email)=upper(:email)")
    List<Product> findByName(String name,String email);
    @Query("SELECT p FROM Product  p where upper( p.name) like upper(concat(:name,'%'))")
    List<Product> findByNameProduct(String name);
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
    @Query("select p from Product p where upper(p.user.email)=upper(:email) order by p.name ASC")
    Page<Product> findByEmailASC(String email,Pageable pageable);
    @Query("select p from Product p where upper(p.user.email)=upper(:email) order by p.name DESC")
    Page<Product> findByEmailDESC(String email,Pageable pageable);
    @Query("select count(p) from Product p where upper(p.user.email)=upper(:email) ")
    Integer findByEmailCount(String email);
    @Query("select count(p) from Product p where upper(p.user.email)=upper(:email)")
    Integer countByEmail(String email);
    @Query("SELECT COUNT(DISTINCT o) FROM Order o JOIN o.productsSet p WHERE upper(p.name) = upper(:name) and o.orderDate between :date and :now ")
    Integer findCountNameByOrdersSet(String name, LocalDateTime date ,LocalDateTime now);
    @Query("SELECT COUNT(DISTINCT o) FROM Order o JOIN o.productsSet p WHERE upper(p.name) = upper(:name) and o.orderDate between :date and :now and upper(p.user.email)=upper(:email)")
    Integer findCountNameByOrdersSetEmail(String name, LocalDateTime date ,LocalDateTime now, String email);
    @Query("select p from Product p ")
    List<Product>  getProductsByCategory( );//don't use
    @Query("select p from Product p where upper(p.user.email)=upper(:email)")
    List<Product> findByEmail(String email);
    void deleteByReceiptNo(String no);
    @Query("select count(p) from Product p where upper(p.category.name)=upper(:name) and upper(p.user.email)=upper(:email)")
    int findCountCategoryByName(String email,String name);
    @Query("select c from Category c where upper(c.name)=upper(:name)")
      Category findByName(String name);
    @Query("select sum(p.price*p.size) from Product p where upper(p.user.email)=upper(:email)")
    Long findIncomeByEmail(String email);
//    @Query("select distinct (p.category.name) from Product p where p.category.name=:name and p.category.userDetail.email=:email")
//    String  findCategoryByName(String email,String name);
@Modifying
@Transactional
@Query("delete from Product p where upper(p.user.email)=upper(:email)")
void deleteByEmail(String email);
@Query("select count(p) from Product p")
Integer getProductCount();
@Query("select sum(p.stock) from Product p where upper(p.user.email)=upper(:email)")
Integer getProductSize(String email);
}
