package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.UserPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
   @Query("select u from UserPermission  u where u.user.email=:email")
   List<UserPermission> findByEmail(String email);
   void deleteByEmail(String email);
   @Query("select distinct p from UserPermission p where p.user.email=:email and p.email<>:email  ")
   Page<UserPermission> findByEmail(String email, Pageable pageable);
   @Modifying
   @Query("DELETE FROM UserPermission u WHERE u.user.email = :email AND u.email = :useremail")
   void deleteByEmailAndUser( String email,  String useremail);
   @Query("select count(distinct (u.email)) from UserPermission  u where u.user.email=:email and u.email<>:email")
   Integer countByEmail(String email);
   @Query("select distinct p from UserPermission p where p.user.email=:email and p.email=:emailUser ")
   UserPermission findByEmailList(String email, String emailUser);
}
