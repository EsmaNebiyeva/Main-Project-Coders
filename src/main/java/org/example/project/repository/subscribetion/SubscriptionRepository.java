package org.example.project.repository.subscribetion;

import org.example.project.entity.subscribetion.Subscription;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  @Query("select s from Subscription s where s.user.email=:email")
    List<Subscription> findAllByEmail(String email);

}
