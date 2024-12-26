package org.example.project.service.general;

import org.example.project.entity.general.BusinessDetails;
import org.example.project.model.BusinessDetailsDTO;


public interface BusinessDetailsService {
    BusinessDetailsDTO saveBusinessDetails(String email,BusinessDetails businessDetails);
    BusinessDetailsDTO getBusinessDetails(String email);
    boolean cancelBusinessDetails(BusinessDetails businessDetails);
}
