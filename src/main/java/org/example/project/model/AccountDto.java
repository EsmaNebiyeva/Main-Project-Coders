package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import org.example.project.entity.general.Address;
import org.example.project.entity.other.Account;

import java.util.Date;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private String name;
    private String password;
    private String phoneNumber;
    private String email;

    public AccountDto(String name, String password, String gender) {
        this.name = name;
        this.password = password;
        this.gender = gender;
    }

    public AccountDto() {
    }

    private Date birthDate;
    private String gender;
private String image;

    public static AccountDto convertToDto(Account address) {
        AccountDto dto = new AccountDto();
        dto.setName(address.getName());
        dto.setGender(address.getGender());
        dto.setBirthDate(address.getBirthDate());
        dto.setEmail(address.getEmail());
        dto.setPassword(address.getPassword());
        dto.setPhoneNumber(address.getPhone());
        dto.setImage(address.getImage());
        return dto;
    }


    public static Account fromDTOToNormal(AccountDto account) {
        Account dto = new Account();
        dto.setName(account.getName());
        dto.setGender(account.getGender());
        dto.setBirthDate(account.getBirthDate());
        dto.setEmail(account.getEmail());
        dto.setPassword(account.getPassword());
        dto.setPhone(account.getPhoneNumber());
        dto.setImage(account.getImage());
        return dto;
    }
}
