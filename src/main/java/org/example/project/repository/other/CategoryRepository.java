package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.name=:name and c.id=:id")
     Category findByNameAndId(String name, Long id);
    @Query("SELECT c FROM Category c WHERE c.name = :name AND c.userDetail.email = :email")
     Category findByName(String name,String email);
}
