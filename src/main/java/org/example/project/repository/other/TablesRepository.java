package org.example.project.repository.other;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.example.project.entity.other.Tablesss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TablesRepository extends JpaRepository<Tablesss, Long> {
    
    @Query("select t from Tablesss t where t.date >= :now")
    List<Tablesss> findByExpirationTimeBefore(LocalDateTime now);
    
    // Süresi dolmuş tokenları silmek için
    @Query("select t from Tablesss t where upper(t.number) = upper(:number)")
    Tablesss findByNumber(String number);
    @Query("select t from Tablesss t where t.order=:orderNum")
    List<Tablesss> findByOrderNumber(Long orderNum);
    @Transactional
    @Modifying
    @Query("DELETE FROM Tablesss t WHERE t.date <= :now")
    void deleteExpiredTokens(LocalDate now);
    @Query("select t from Tablesss t where upper(t.access)=upper('REZERV')")
    List<Tablesss>  findRezervTables();
    @Query("select t from Tablesss t where upper(t.access)=upper('SOON')")
    List<Tablesss>  findSoonTables();
}
