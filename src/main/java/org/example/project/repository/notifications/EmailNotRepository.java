package org.example.project.repository.notifications;




import org.example.project.entity.notification.EmailNot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import jakarta.transaction.Transactional;


@EnableJpaRepositories
@Transactional
public interface EmailNotRepository extends JpaRepository<EmailNot, Long> {
    @Query("select b from EmailNot b where upper(b.email) = upper(:email)")
    EmailNot findByEmail(String email);

}