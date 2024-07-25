package org.example.project.service;

import org.example.project.entity.BusinessDetails;
import org.springframework.stereotype.Service;

public interface BusinessDetailsService {
    void saveBusinessDetails(BusinessDetails businessDetails);

    BusinessDetails cancelBusinessDetails(BusinessDetails businessDetails);
}
