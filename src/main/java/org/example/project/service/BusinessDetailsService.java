package org.example.project.service;

import org.example.project.entity.BusinessDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface BusinessDetailsService {
    BusinessDetails saveBusinessDetails(BusinessDetails businessDetails);

    Optional<BusinessDetails> cancelBusinessDetails(BusinessDetails businessDetails);
}
