package org.example.project.model;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.example.project.entity.other.*;

import org.example.project.repository.general.SettingRepository;
import org.example.project.repository.other.AccountRepository;
import org.example.project.security.token.Token;
import org.example.project.security.user.Role;
import org.example.project.security.user.UserDetail;

import java.util.*;


import static org.example.project.model.SettingDTO.convertToSettingDTO;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public  class UserDTO {
    private Integer id;
    private String firstname;
  
    private String lastname;
    private String email;
    //private LocalDateTime created;
private String imageUrl;
    private String phoneNumber;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    // private Set<ProductDTO> products = new HashSet<>();

    // //(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private Set<OrderDTO> orders = new HashSet<>();

    // private Set<Account> account = new HashSet<>();

    // private Address address;

    // private Set<Confirmation> confirmations = new HashSet<>();

    // private Set<UserPermission> userPermission = new HashSet<>();

    // private Set<SubscriptionDTO> subscriptions = new HashSet<>();
    // private BusinessDetails businessDetails;
    private SettingDTO setting;
    private List<TokenDto> tokens;
  private AccountDto account;
    public static UserDTO convertToUserDto(UserDetail userDetail,SettingRepository repository,AccountRepository accountRepository,SettingRepository settingRepository  ) {
        UserDTO dto = new UserDTO();
        Optional<Setting> byEmail = repository.findByEmail(userDetail.getEmail());
        if( byEmail.isPresent()){
             SettingDTO convertToDTOSettingDTOA = convertToSettingDTO(byEmail.get());
        dto.setSetting(convertToDTOSettingDTOA);
        }
        dto.setId(userDetail.getId());
        dto.setFirstname(userDetail.getFirstname());
        dto.setLastname(userDetail.getLastname());
        dto.setEmail(userDetail.getEmail());
        dto.setImageUrl(userDetail.getImageUrl());
       // dto.setCreated(userDetail.getCreated());
        dto.setPhoneNumber(userDetail.getPhoneNumber());
        dto.setPassword(userDetail.getPassword());
        dto.setRole(userDetail.getRole());
        Optional<Account> byEmail2 = accountRepository.findByEmail(userDetail.getEmail());
if(byEmail2.isPresent()){
    AccountDto convertToDto2 = AccountDto.convertToDto(byEmail2.get());
        dto.setAccount(convertToDto2);
}
        // Set<Product> products1 = userDetail.getProducts();
        // Set<ProductDTO> orders2 = new HashSet<>();
        // for(Product product : products1) {
        //     orders2.add(convertToDto(product));
        // }
        // dto.setProducts(orders2);
        // Set<Order> orders1 = userDetail.getOrders();
        // Set<OrderDTO> orders3 = new HashSet<>();
        // for(Order order : orders1) {
        //   orders3.add(OrderDTO.converToDTO(order));
        // }
        // dto.setOrders(orders3);
        // dto.setAccount(userDetail.getAccount());
        // dto.setAddress(userDetail.getAddress());
        // dto.setConfirmations(userDetail.getConfirmations());
        // dto.setUserPermission(userDetail.getUserPermission());
        // Set<SubscriptionDTO> subscriptions1 = new HashSet<>();
        // for(Subscription subscription : userDetail.getSubscriptions()) {
        //     subscriptions1.add(convertToDTO(subscription));
        // }
        // dto.setSubscriptions(subscriptions1);
        // dto.setBusinessDetails(userDetail.getBusinessDetails());
        
        List<Token> tokens1 = userDetail.getTokens();
        List<TokenDto> tokens2 = new ArrayList<>();
        for(Token product : tokens1) {
           if( !product.expired && !product.revoked) {

               tokens2.add(new TokenDto(product.getToken()));
           }
        }
       dto.setTokens(tokens2);
    //    Optional<Setting> byEmail3 = settingRepository.findByEmail(userDetail.getEmail());
    //    if(byEmail3.isPresent()){
    //     dto.setSetting(byEmail3.get());
    //    }
       return dto;
    }

}
