package org.example.project.service.general;

import org.example.project.entity.general.BusinessDetails;

import java.util.Optional;

public interface BusinessDetailsService {
    BusinessDetails saveBusinessDetails(BusinessDetails businessDetails);

    Optional<BusinessDetails> cancelBusinessDetails(BusinessDetails businessDetails);
}
