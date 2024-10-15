package org.example.project.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserDetail, Integer> {
  Optional<UserDetail> findByEmail(String email);
  @Query("select u from UserDetail u where u.email=:email")
  UserDetail getByEmail( String email);

}
