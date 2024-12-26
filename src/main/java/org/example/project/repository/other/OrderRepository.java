package org.example.project.repository.other;

import jakarta.transaction.Transactional;
import org.example.project.entity.other.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select a from Order a  where  a.orderDate<=: localDate  or a.orderDate>=: date ")
    Page<Order> findAllBy(Pageable pageable, LocalDate date, LocalDate localDate);
//    @Query("select a from Order a  where  (a.orderDate<=: localDate  or a.orderDate>=: date) and a.user.email=:email ")
//    Page<Order> findAllBy(Pageable pageable, LocalDate date, LocalDate localDate,String email);
    @Query("SELECT a FROM Order a WHERE (a.orderDate <= :endDate AND a.orderDate >= :startDate) AND upper(a.user.email) = upper(:email) ORDER BY orderDate ASC")
    Page<Order> findAllByASCs(Pageable pageable,  LocalDateTime startDate,  LocalDateTime endDate, String email);
    @Query("SELECT a FROM Order a WHERE (a.orderDate <= :endDate AND a.orderDate >= :startDate) AND upper(a.user.email) = upper(:email) ORDER BY orderDate DESC")
    Page<Order> findAllByDESCs(Pageable pageable,  LocalDateTime startDate,  LocalDateTime endDate, String email);
    @Query("SELECT count(a) FROM Order a WHERE (a.orderDate <= :endDate AND a.orderDate >= :startDate) AND upper(a.user.email) = upper(:email) ")
    Integer findAllByASCsCount(  LocalDateTime startDate,  LocalDateTime endDate, String email);
    @Query("SELECT count(a) FROM Order a WHERE (a.orderDate <= :endDate AND a.orderDate >= :startDate) AND upper(a.user.email) = upper(:email) ")
   Integer findAllByDESCsCount(  LocalDateTime startDate,  LocalDateTime endDate, String email);
    @Query("SELECT a FROM Order a WHERE  upper(a.user.email) = upper(:email)")
    Page<Order> findAllBy(Pageable pageable,  String email);
    @Query("SELECT count(a) FROM Order a WHERE  upper(a.user.email) = upper(:email ) ")
    Integer findAllByASCCount(  String email);
    @Query("SELECT count(a) FROM Order a WHERE  upper(a.user.email) = upper(:email) ")
    Integer findAllByDESCCount(  String email);
    @Query("SELECT a FROM Order a WHERE  upper(a.user.email) = upper(:email ) ORDER BY orderDate ASC")
    Page<Order> findAllByASC(Pageable pageable,  String email);
    @Query("SELECT a FROM Order a WHERE  upper(a.user.email) = upper(:email) ORDER BY orderDate DESC")
    Page<Order> findAllByDESC(Pageable pageable,  String email);
    @Query("SELECT p FROM Order  p where p.orderId=:orderId and upper(p.user.email)=upper(:email)")
    Order findByOrderIdEmail(Long orderId,String email);
    @Query("SELECT p FROM Order  p where p.orderId=:orderId and upper( p.user.email)=upper(:email)")
    Optional<Order> findByOrderId(Long orderId,String email);
    @Query("SELECT p FROM Order  p where p.orderId=:orderId")
    Optional<Order> findByOrderId(Long orderId);
    @Query("select p from Order p where (p.orderDate between :date and :now) and upper(p.user.email)=upper(:email) ")
    List<Order> getTotalPrice(LocalDate now, LocalDate date,String email);
    @Query("select o from Order o where upper(o.user.email)=upper(:email)")
    List<Order> findByEmail(String email);
    @Query("select count(o) from Order o where upper(o.user.email)=upper(:email)")
    Integer countOrderByEmail(String email);
    @Query("select o from Order o where upper(o.user.email)=upper(:email)")
    List<Order> findByEmailAndReceiptNo(String email);
    void deleteByOrderId(Long id);
    @Modifying
    @Transactional
    @Query("delete from Order o where upper(o.user.email)=upper(:email)")
    void deleteByEmail(String email);
@Query("select o from Order o where upper(o.user.email)=upper(:email)")
    List<Order> findOrders(String email);
   // @Query("select o from Order o where")
//    @Query("select p.name from Order o join o.productsSet p on o.id = :orderId where o.orderDate=:date")
//    String countOrderByProductsSet(String product_name, LocalDate date);
  //  void deleteByOrderId(Long orderId);
//    @Query("select sum(a.) from Order a w")
  //  @Query("select p ")
@Query("select distinct(o.userName) from Order o")
  List<String>  findByDistinctCashier(String email);
  @Query("select o from Order o where CAST(o.orderId AS string) like CONCAT(:id, '%') and upper(o.user.email) = upper(:email)")
    List<Order> findByOrderIdAndUser(String id,String email);
    @Query("select sum(o.totalPrice) from Order o where TO_CHAR(o.orderDate, 'YYYY-MM')= concat(concat(:year,'-'),:month) and upper(o.user.email)=upper(:email)")
    Optional<Double>  getIncomeByMonth(String year,String month,String email);
    @Query("select sum(o.totalPrice) from Order o where TO_CHAR(o.orderDate, 'YYYY')= :year and upper(o.user.email)=upper(:email)")
    Optional<Double>  getIncomeByYear(String year,String email);
    @Query("select o from Order o where TO_CHAR(o.orderDate, 'YYYY-MM')= concat(concat(:year,'-'),:month) and upper(o.user.email)=upper(:email)")
    List<Order>  getIncomeByMonthEmail(String year,String month,String email);
    @Query("select sum(o.totalPrice) from Order o where TO_CHAR(o.orderDate, 'YYYY-MM-DD')= concat(concat(concat(concat(:year,'-'),:month),'-'),:day) and upper(o.user.email)=upper(:email) GROUP BY TO_CHAR(o.orderDate, 'YYYY-MM-DD')")
    Optional<Double>  getIncomeByDay(String year,String month,String day,String email);
    @Query("SELECT DISTINCT EXTRACT(YEAR FROM o.orderDate) FROM Order o WHERE upper(o.user.email) = upper(:email)")
    List<String> getDistinctYears(String email);
    @Query("select o from Order o  where TO_CHAR(o.orderDate, 'YYYY-MM-DD')= concat(concat(concat(concat(:year,'-'),:month),'-'),:day) ")
    List<Order> getByDate(String year,String month,String day );
}
