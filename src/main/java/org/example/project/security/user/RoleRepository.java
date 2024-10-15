//package org.example.project.security.user;
//
//import jakarta.transaction.Transactional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@Transactional
//public interface RoleRepository extends JpaRepository<Role,Long> {
//    @Query("select r from Role r where r.name=:name ")
//    Role findByName(String name);
//}
