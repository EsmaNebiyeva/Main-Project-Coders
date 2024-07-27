package org.example.project.repository;

import jakarta.transaction.Transactional;
import org.example.project.entity.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Long> {
    @Query("SELECT p FROM BusinessDetails  p where upper(p.businessEmail)=upper(:name)")
    Optional<BusinessDetails> findByBusinessEmail(String name);
}
