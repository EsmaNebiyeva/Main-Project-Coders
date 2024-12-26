package org.example.project.repository.subscribetion;

import java.util.Optional;

import org.example.project.entity.subscribetion.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Long> {
   @Query("SELECT p FROM Payment p WHERE upper(p.name) = upper(:#{#payment.name}) AND p.number = :#{#payment.number} AND p.name = :#{#payment.name} AND p.cvv = :#{#payment.cvv} AND p.date = :#{#payment.date}")
Optional<Payment> findByNumberAndNameAndCvvAndDate( Payment payment);

}
