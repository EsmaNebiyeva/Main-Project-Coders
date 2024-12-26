package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account  a where  upper(a.email)=upper(:name)")
    Optional<Account> findByEmail(String name);
    // @Query("select u FROM Account u WHERE  upper(u.email) = upper(:email) ")
    // Account findByEmail( String email);
    @Query("SELECT a FROM Account  a where upper(a.email)=upper(:name)")
    Optional<Account> findByEmailFirst(String name);
    @Query("SELECT a FROM Account  a where  a.deletedDate<= :now")
    List<Account> findByEmail(LocalDate now);
    @Modifying
    @Transactional
    @Query("delete from Account a where upper(a.email)=upper(:name)")
    void deleteByDate(String name);
}
