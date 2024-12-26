package org.example.project.security.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import jakarta.transaction.Transactional;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserDetail, Integer> {
  Optional<UserDetail> findByEmail(String email);
  @Query("select u from UserDetail u where upper(u.email)=upper(:email)")
  UserDetail getByEmail( String email);
  @Query("select u from UserDetail u where upper(u.email)=upper(:email)")
  Optional<UserDetail> getByEmailsDetail( String email);
  @Modifying
  @Transactional
 void deleteByEmail(String email);
 @Query("select u from UserDetail u where upper(u.email)=upper(:email) and upper(u.password)=upper(:password) ")
 UserDetail findByEmailAndPassword(String email,String password);
   @Query("select  p from UserDetail p where upper(p.email)<>upper(:email)  ")
   Page<UserDetail> findByEmails(String email, Pageable pageable);
   @Query("select count( p) from UserDetail p where  upper(p.email)<>upper(:email) ")
   Integer findByEmailList( String email);
}
