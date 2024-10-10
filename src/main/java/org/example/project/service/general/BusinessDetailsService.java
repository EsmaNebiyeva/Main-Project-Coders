package org.example.project.service.general;

import org.example.project.entity.general.BusinessDetails;
import org.example.project.model.BusinessDetailsDTO;

import java.util.Optional;

public interface BusinessDetailsService {
    BusinessDetails saveBusinessDetails(BusinessDetailsDTO businessDetails);

    Optional<BusinessDetails> cancelBusinessDetails(BusinessDetailsDTO businessDetails);
}
