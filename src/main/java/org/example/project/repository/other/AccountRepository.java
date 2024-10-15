package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account  a where a.userDetail.email=:name")
    Optional<Account> findByEmail(String name);
    @Query("select u FROM Account u WHERE u.userDetail.email = :email AND u.email = :useremail")
    Account findByEmailAndUser( String email,  String useremail);
    @Query("SELECT a FROM Account  a where a.email=:name")
    Optional<Account> findByEmailFirst(String name);
}
