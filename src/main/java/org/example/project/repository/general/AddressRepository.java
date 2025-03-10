package org.example.project.repository.general;

import jakarta.transaction.Transactional;
import org.example.project.entity.general.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select a from Address a where upper(a.userDetails.email)=upper(:name)")
    Optional<Address> findByEmail(String name);


}
