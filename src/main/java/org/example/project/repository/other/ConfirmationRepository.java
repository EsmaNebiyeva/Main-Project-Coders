package org.example.project.repository.other;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.example.project.entity.other.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
//@EnableJpaRepositories(basePackageClasses = Confirmation.class)
@Transactional
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    @Query("select c from Confirmation c where upper(c.user)=upper(:email) and upper(c.randomPassword)=upper(:password) ")
    Confirmation findByEmailAndPassword(String email, String password);
}
