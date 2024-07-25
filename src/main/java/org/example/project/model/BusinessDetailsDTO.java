package org.example.project.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BusinessDetailsDTO {
    private Long id;
    private String storeName;
    private String number;
    private String businessEmail;
    private String fax;
    private UserDTO user;

}
