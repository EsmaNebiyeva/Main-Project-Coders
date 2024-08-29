package org.example.project.repository.other;

import org.example.project.entity.other.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
