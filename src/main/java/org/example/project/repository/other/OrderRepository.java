package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.Optional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select a from Order a where a.orderDate<=: localDate  or a.orderDate>=: date")
    Page<Order> findAllBy(Pageable pageable, LocalDate date, LocalDate localDate);
    @Query("SELECT p FROM Order  p where p.orderId=:orderId")
    Optional<Order> findByOrderId(Long orderId);
    @Query("select sum(p.totalPrice) from Order p where p.orderDate between :date and :now ")
    Long getTotalPrice(LocalDate now, LocalDate date);
  //  void deleteByOrderId(Long orderId);
//    @Query("select sum(a.) from Order a w")
  //  @Query("select p ")


}
