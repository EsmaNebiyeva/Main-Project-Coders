package org.example.project.repository.notifications;



import org.example.project.entity.notification.MoreActivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.transaction.Transactional;
@Transactional
@EnableJpaRepositories
public interface MoreActivityRepository extends JpaRepository<MoreActivity, Long> {
    @Query("select b from MoreActivity b where upper(b.email)=upper(:email)")
    MoreActivity findByEmail(String email);

}