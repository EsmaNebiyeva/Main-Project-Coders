package org.example.project.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.general.Address;
import org.example.project.entity.general.BusinessDetails;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BusinessDetailsDTO {
    private Long id;
    private String storeName;
    private String number;
    private String businessEmail;
    private String fax;
    public static BusinessDetailsDTO convertToDto(BusinessDetails businessDetails) {
        BusinessDetailsDTO dto = new BusinessDetailsDTO();
        dto.setStoreName(businessDetails.getStoreName());
        dto.setFax(businessDetails.getFax());
        dto.setBusinessEmail(businessDetails.getBusinessEmail());
        dto.setNumber(businessDetails.getNumber());
        return dto;
    }


    public static BusinessDetails fromDTOToNormal(BusinessDetailsDTO businessDetails) {
        BusinessDetails businessDetails1 = new BusinessDetails();
        businessDetails1.setStoreName(businessDetails.getStoreName());
        businessDetails1.setFax(businessDetails.getFax());
        businessDetails1.setBusinessEmail(businessDetails.getBusinessEmail());
        businessDetails1.setNumber(businessDetails.getNumber());
        return businessDetails1;
    }

}
