package org.example.project.repository.notifications;

import java.util.List;

import org.example.project.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Transactional
public interface NotificationRepository  extends JpaRepository<Notification,Long>{
    @Query("select n from Notification n where upper(n.email)=upper(n.email)")
    List<Notification> findByEmail(String email);
}
