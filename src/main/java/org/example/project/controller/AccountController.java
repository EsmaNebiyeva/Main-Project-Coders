package org.example.project.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.other.Account;
import org.example.project.model.AccountDto;
//import org.example.project.model.EmailNotDTO;
import org.example.project.model.MoreActivitiesEnum;
import org.example.project.model.MoreActivityDTO;
import org.example.project.repository.general.SettingRepository;
import org.example.project.repository.other.AccountRepository;
import org.example.project.repository.other.ConfirmationRepository;
import org.example.project.repository.other.OrderRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.repository.other.UserPermissionRepository;
import org.example.project.repository.subscribetion.SubscriptionRepository;
import org.example.project.security.auth.MailService;
import org.example.project.security.config.JwtService;
import org.example.project.security.token.TokenRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserService;
//import org.example.project.service.notifications.EmailNotService;
import org.example.project.service.notifications.MoreActivityService;
import org.example.project.service.other.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import static org.example.project.model.AccountDto.fromDTOToNormal;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor()
@CrossOrigin(origins = {"http://localhost:4444", "https://posive.huseyn.site/","https://posive.vercel.app/"})
//ALINDIiiiiiii
public class AccountController {
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final AccountRepository arepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final TokenRepository repository;
    @Autowired
    private final ConfirmationRepository cRepository;
    @Autowired
    private final ProductRepository productService;
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserPermissionRepository uRepository;
    @Autowired
    private final SubscriptionRepository sRepository;
    @Autowired 
    private final SettingRepository sRepository2;
    //    @Autowired
    // private final EmailNotService emailNotService;
    @Autowired
    private final MoreActivityService moreActivityService;
    @Autowired
    private final MailService mailService;

    @Transactional
    @DeleteMapping("/deleteByEmails")
    public ResponseEntity<String> deleteByEmails(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
    
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                // Delete any tokens associated with the user first
                repository.deleteByUserId(user.getId());
    
                // Proceed with deleting other associated records
                productService.deleteByEmail(email);
                orderRepository.deleteByEmail(email);
                cRepository.deleteByEmail(email);
                uRepository.deleteByEmail(email);
                sRepository.deleteByEmail(email);
    
                // Finally, delete the user
                if (userService.deleteByEmail(email)) {
                    return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("Failed to delete user", HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/save")
    public ResponseEntity<AccountDto> save(HttpServletRequest request, @RequestBody AccountDto account) throws MessagingException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null ) {
                 MoreActivityDTO activity = moreActivityService.getActivity(email);
       // EmailNotDTO activity2 = emailNotService.getActivity(email);
                  Account account1 = fromDTOToNormal(account);
                account1.setUserDetail(user);
                
                AccountDto accountDto = accountService.saveAccount(email, account1);
     if(accountDto==null){
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
                      if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
                mailService.sendAccount(email);
                }
                return new ResponseEntity<>(accountDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
@GetMapping("/get")
public ResponseEntity<AccountDto> get(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                AccountDto account = accountService.getAccount(email);
                return new ResponseEntity<>(account,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
    @PutMapping("/cancel")
    public ResponseEntity<String> cancel(HttpServletRequest request, @RequestBody Account account) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                accountService.cancelAccount(account);
                return new ResponseEntity<>("Cancel oldu", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
@DeleteMapping("/deleting")
public ResponseEntity<String> deleting(HttpServletRequest request) throws MessagingException {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        // Create order and associate with user
        UserDetail user = userService.findByEmail(email);
        if (user != null) {
            MoreActivityDTO activity = moreActivityService.getActivity(email);
          //  EmailNotDTO activity2 = emailNotService.getActivity(email);
           Optional<Account> byEmail = arepository.findByEmail(email);
           if(byEmail.isPresent()){
           byEmail.get().setDeletedDate(LocalDate.now().plusDays(14));
           arepository.save(byEmail.get());
           if(activity.getActivity().contains(MoreActivitiesEnum.IMPORTANT.name())|| activity.getActivity().contains(MoreActivitiesEnum.ALLREMINDERS.name())){
            mailService.sendAccountDeleting(email);
            }
           return new ResponseEntity<>(HttpStatus.OK);
           }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}


// @DeleteMapping("/auto")
//  public ResponseEntity<String> delete1(HttpServletRequest request) {
//     String authorizationHeader = request.getHeader("Authorization");
//     if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//         String token = authorizationHeader.substring(7);
//         String email = jwtService.extractEmail(token);

//         // Create order and associate with user
//         UserDetail user = userService.findByEmail(email);
//         if (user != null) {
//             Optional<Account> byEmail = arepository.findByEmail(email);
//            if(byEmail.isPresent()){
//             Boolean cleanupDeletedAccounts = accountService.cleanupDeletedAccounts(email);
//             if(cleanupDeletedAccounts){
//                 return new ResponseEntity<>("Silindi",HttpStatus.OK);
//             }
//            }
//         }
//         return new ResponseEntity<>("Silinmedi",HttpStatus.BAD_REQUEST);
//     }
// }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(HttpServletRequest request, @RequestBody AccountDto account) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {
                Account fromDTOToNormal2 = fromDTOToNormal(account);
                if (accountService.deleteAccount(email,fromDTOToNormal2)) {
                    return new ResponseEntity<>("Delete oldu", HttpStatus.ACCEPTED);
                } else{
                    return new ResponseEntity<>("Delete olmadi",HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteByEmail")
    public ResponseEntity<String> deleteByEmail(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractEmail(token);

            // Create order and associate with user
            UserDetail user = userService.findByEmail(email);
            if (user != null) {

                if (accountService.deleteAccountByEmail(email)) {
                    repository.deleteByUserId(user.getId());
                    cRepository.deleteByEmail(email);
                    productService.deleteByEmail(email);
                    orderRepository.deleteByEmail(email);
                    sRepository2.deleteByEmail(email);
                    uRepository.deleteByEmail(email);
                    sRepository.deleteByEmail(email);

                     Boolean deleteByEmail = userService.deleteByEmail( email);
                    if(deleteByEmail){
                    return new ResponseEntity<>( HttpStatus.ACCEPTED);
                } else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }}
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

