package org.example.project.repository.subscribetion;

import org.example.project.entity.subscribetion.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("select p from Plan p where upper(p.name)=upper(:name)")
    Plan findByName(String name);
}
