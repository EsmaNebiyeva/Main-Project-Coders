package org.example.project.repository;

import jakarta.transaction.Transactional;
import org.example.project.entity.Roles;
import org.example.project.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
}
