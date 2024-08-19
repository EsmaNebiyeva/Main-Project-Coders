package org.example.project.repository;

import jakarta.transaction.Transactional;
import org.example.project.entity.Order;
import org.example.project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select a from Order a where a.orderDate<=: localDate  or a.orderDate>=: date  ")
    Page<Order> findAllBy(Pageable pageable, LocalDate date, LocalDate localDate);
    @Query("SELECT p FROM Order  p where upper(p.orderId)=upper(:orderId)")
    Optional<Order> findByOrderId(String orderId);
    @Query("delete  FROM Order  p where upper(p.orderId)=upper(:orderId)")
    void deleteByOrderId(String orderId);
}
