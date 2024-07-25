package org.example.project.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@RequiredArgsConstructor

public  class UserDTO {
    private Long id;

    private String username;


    private String email;


    private String phoneNumber;

    private String password;
    private String role;

    private List<ProductDTO> products;

    private List<OrderDTO> orders;

    private Date birthDate;

    private Date signUpDate;

    private String accessType;

    private String gender;

    private AddressDTO address;


}
