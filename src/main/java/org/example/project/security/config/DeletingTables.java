package org.example.project.security.config;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.project.entity.other.Access;
import org.example.project.entity.other.Account;
import org.example.project.entity.other.Order;
import org.example.project.entity.other.Product;
import org.example.project.entity.other.Tablesss;
import org.example.project.model.EmailNotDTO;
import org.example.project.model.EmailNots;
import org.example.project.model.MoreActivitiesEnum;
import org.example.project.model.MoreActivityDTO;
import org.example.project.repository.other.AccountRepository;
import org.example.project.repository.other.ConfirmationRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.repository.other.TablesRepository;
import org.example.project.repository.other.UserPermissionRepository;
import org.example.project.repository.subscribetion.SubscriptionRepository;
import org.example.project.security.auth.MailService;
import org.example.project.security.token.TokenRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;
import org.example.project.service.notifications.EmailNotService;
import org.example.project.service.notifications.MoreActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class DeletingTables {
@Autowired
private final TablesRepository tablesRepository;
@Autowired
private final AccountRepository accountRepository;
@Autowired
private final OrderRepository repository;
@Autowired
private final UserRepository userRepository;
  private final TokenRepository repositoryToken;
    @Autowired
    private final ConfirmationRepository cRepository;
    @Autowired
    private final ProductRepository productService;
@Autowired
private final MailService mailService;
    @Autowired
    private final UserPermissionRepository uRepository;
    @Autowired
    private final SubscriptionRepository sRepository;
    @Autowired
    private final EmailNotService emailNotService;
    @Autowired
    private final MoreActivityService moreActivityService;
   

@Scheduled(fixedRate = 60000) // 1 saat = 3600000 ms
    public void removeExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<Tablesss> soonTables = tablesRepository.findSoonTables();
        for(Tablesss tablesss:soonTables){
            Duration duration=Duration.between(tablesss.getDate(), now);
            if(duration.toMinutes()>=60){
            Optional<Order> byOrderId = repository.findByOrderId(tablesss.getOrder());
            if(byOrderId.isPresent()){
                Order order = byOrderId.get();
                List<String> tables = order.getTables();
                tables.remove(tablesss.getNumber());
                System.out.println(tablesss.getNumber());
                order.setTables(tables);
                System.out.println(tables);
                repository.save(order);
            } 
            
            tablesss.setAccess(Access.AVALIABLE);
            tablesss.setDate(null);
            tablesss.setOrder(null); 

            tablesRepository.save(tablesss);
        }

        }
        System.out.println("Süresi dolmuş tables temizlendi: " + now);
    }

    // @Scheduled(fixedRate = 60000) // 1 saat = 3600000 ms
    // public void getMessageByOrder() throws MessagingException {
    //     List<UserDetail> all = userRepository.findAll();
    //     for(UserDetail user:all){
    //         EmailNotDTO activity = emailNotService.getActivity(user.getEmail());
    //         MoreActivityDTO activity2 = moreActivityService.getActivity(user.getEmail());
    //         if(activity.getNots().contains(EmailNots.OFFER.name())||activity2.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
    //     Integer countOrderByEmail = repository.countOrderByEmail(user.getEmail());
    //     if(countOrderByEmail%10==0){
    //         mailService.sendOrderCount(user.getEmail(),countOrderByEmail);
    //         System.out.println("MAIL ORDER GONDERILDI SCHEDULED");
    //     }
    // }
    //     }
    // }
    @Scheduled(fixedRate = 60000) // 1 saat = 3600000 ms
    public void getIncomeByDay() throws MessagingException {
        List<UserDetail> all = userRepository.findAll();
        for(UserDetail user:all){
           // EmailNotDTO activity = emailNotService.getActivity(user.getEmail());
            MoreActivityDTO activity2 = moreActivityService.getActivity(user.getEmail());
            if(activity2.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())|| activity2.getActivity().contains(MoreActivitiesEnum.ACTIVITY.name())){
            LocalDateTime now =LocalDateTime.now();
            LocalDateTime month=now.minusMonths(1);
            Double total1 = 0.0;
            List<Order> totalPrice = repository.getIncomeByMonthEmail(String.valueOf(now.getYear()),String.valueOf( now.getMonth()),user.getEmail());
            if (!totalPrice.isEmpty()) {
                for (Order order : totalPrice) {
                    for (Product product : order.getProductsSet()) {
                        total1 = total1 + product.getPrice() + product.getPrice() * product.getTax()
                                - product.getDiscount() * product.getPrice();
                    }
                }
            }
            Double total2 = 0.0;
            List<Order> totalPrice1Orders = repository.getIncomeByMonthEmail(String.valueOf(month.getYear()),String.valueOf( month.getMonth()),user.getEmail());
            if (!totalPrice1Orders.isEmpty()) {
               
                for (Order order : totalPrice1Orders) {
                    for (Product product : order.getProductsSet()) {
                        total2 = total2 + product.getPrice() + product.getPrice() * product.getTax()
                                - product.getDiscount() * product.getPrice();
                    }
                }
            }
            if(total1>total2){
                mailService.sendOrderIncome(user.getEmail(), total1-total2);
            }
        }
    }
    }


    @Scheduled(fixedRate = 60000) // 1 saat = 3600000 ms
    public void deleteOrders() throws MessagingException {
    List<Order> all = repository.findAll();
    for(Order o: all){
        MoreActivityDTO activity = moreActivityService.getActivity(o.getUser().getEmail());
        EmailNotDTO activity2 = emailNotService.getActivity(o.getUser().getEmail());
        List<Product> productsSet = o.getProductsSet();
        if(productsSet.isEmpty()){
            {
                if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())||activity2.getNots().contains(EmailNots.TIPS.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                mailService.sendOrdeString(o.getUser().getEmail(),"DELETE OLUNDU");
                }
            repository.deleteByOrderId(o.getOrderId());
        }
    }
}
    }

    
    @Scheduled(fixedRate = 50000) // 1 saat = 3600000 ms
    public void removeExpiredToken() {
        List<Tablesss> byExpirationTimeBefore = tablesRepository.findRezervTables();
        for(Tablesss tablesss:byExpirationTimeBefore){
            LocalDateTime date = tablesss.getDate();
              LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(date, now);
            if(duration.toMinutes() >= 50){
            tablesss.setAccess(Access.SOON); 
            tablesRepository.save(tablesss);
            }
        }
        System.out.println("Süresi 10 deq  tables temizlendi: " + "soon");
    }

    @Scheduled(cron = "0 0 0 * * ?" ) 
    public void accountDelete() throws MessagingException {
        LocalDate now = LocalDate.now();
        List<Account> byEmail = accountRepository.findByEmail(now);
        
        for(Account account:byEmail){
            MoreActivityDTO activity = moreActivityService.getActivity(account.getEmail());
         //   EmailNotDTO activity2 = emailNotService.getActivity(account.getEmail());
            String email = account.getEmail();
            UserDetail byEmail2 = userRepository.getByEmail(email);
        accountRepository.deleteByDate(email);
         userRepository.deleteByEmail(email);
            productService.deleteByEmail(email);
            repository.deleteByEmail(email);
            cRepository.deleteByEmail(email);
            uRepository.deleteByEmail(email);
            sRepository.deleteByEmail(email);
            repositoryToken.deleteById(byEmail2.getId());
            if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                mailService.sendAccount(email);
                }
        System.out.println("Süresi dolmuş accounts temizlendi: " + email);
    }
    System.out.println("Süresi dolmuş accounts temizlendi: " + now);
}

@Scheduled(fixedRate = 24 * 60 * 60 * 1000) 
    public void size() {
        List<Product> all = productService.findAll();
        for(Product a:all){
            a.setSize(0.0);
            productService.save(a);
        }
        }
        
    }
    
