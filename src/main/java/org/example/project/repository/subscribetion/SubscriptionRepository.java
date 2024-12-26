package org.example.project.repository.subscribetion;

import org.example.project.entity.subscribetion.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import jakarta.transaction.Transactional;


import java.util.List;

@EnableJpaRepositories
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  @Query("select s from Subscription s where upper(s.userEmail)=upper(:email)")
    List<Subscription> findAllByEmail(String email);
    @Modifying
    @Transactional
    @Query("delete from Subscription s where upper(s.userEmail)=upper(:email)")
   void  deleteByEmail(String email);
}
