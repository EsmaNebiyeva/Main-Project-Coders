package org.example.project.repository;

import jakarta.transaction.Transactional;
import org.example.project.entity.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Integer> {
}
