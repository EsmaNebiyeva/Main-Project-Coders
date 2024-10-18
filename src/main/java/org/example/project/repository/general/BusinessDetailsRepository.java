package org.example.project.repository.general;

import jakarta.transaction.Transactional;
import org.example.project.entity.general.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Long> {
    @Query("select b from BusinessDetails b where upper(b.user.email)=upper(:name)")
   Optional<BusinessDetails> findByEmail(String name);

}
