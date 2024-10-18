package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select a from Order a  where  a.orderDate<=: localDate  or a.orderDate>=: date ")
    Page<Order> findAllBy(Pageable pageable, LocalDate date, LocalDate localDate);
//    @Query("select a from Order a  where  (a.orderDate<=: localDate  or a.orderDate>=: date) and a.user.email=:email ")
//    Page<Order> findAllBy(Pageable pageable, LocalDate date, LocalDate localDate,String email);
    @Query("SELECT a FROM Order a WHERE (a.orderDate <= :endDate OR a.orderDate >= :startDate) AND upper(a.user.email) = upper(:email) ")
    Page<Order> findAllBy(Pageable pageable,  LocalDate startDate,  LocalDate endDate, String email);
    @Query("SELECT a FROM Order a WHERE  upper(a.user.email) = upper(:email)")
    Page<Order> findAllBy(Pageable pageable,  String email);
    @Query("SELECT p FROM Order  p where p.orderId=:orderId and upper(p.user.email)=upper(:email)")
    Order findByOrderIdEmail(Long orderId,String email);
    @Query("SELECT p FROM Order  p where p.orderId=:orderId and upper( p.user.email)=upper(:email)")
    Optional<Order> findByOrderId(Long orderId,String email);
    @Query("select p from Order p where (p.orderDate between :date and :now) and upper(p.user.email)=upper(:email) ")
    List<Order> getTotalPrice(LocalDate now, LocalDate date,String email);
    @Query("select o from Order o where upper(o.user.email)=upper(:email)")
    List<Order> findByEmail(String email);
    @Query("select count(o) from Order o where upper(o.user.email)=upper(:email)")
    Integer countOrderByEmail(String email);
    @Query("select o from Order o where upper(o.user.email)=upper(:email)")
    List<Order> findByEmailAndReceiptNo(String email);
    void deleteByOrderId(Long id);
//    @Query("select p.name from Order o join o.productsSet p on o.id = :orderId where o.orderDate=:date")
//    String countOrderByProductsSet(String product_name, LocalDate date);
  //  void deleteByOrderId(Long orderId);
//    @Query("select sum(a.) from Order a w")
  //  @Query("select p ")


}
