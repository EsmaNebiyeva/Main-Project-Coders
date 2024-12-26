package org.example.project.repository.general;

import java.util.Optional;

import org.example.project.entity.other.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public interface SettingRepository  extends JpaRepository<Setting, Long>{
    @Query("select a from Setting a where upper(a.user.email)=upper(:email)")
    Optional<Setting> findByEmail(String email);
    @Modifying
    @Transactional
    @Query("delete from Setting s where  upper(s.user.email)=upper(:email)")
     void deleteByEmail(String email);
}
