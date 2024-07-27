package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.BusinessDetails;
import org.example.project.repository.BusinessDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessDetailsServiceImpl implements BusinessDetailsService{
    @Autowired
    private final BusinessDetailsRepository businessDetailsRepository;
    @Override
    public BusinessDetails saveBusinessDetails(BusinessDetails businessDetails) {
//        businessDetailsRepository.findByBusinessEmail(businessDetails.getBusinessEmail()).map(e -> {
//            e.setBusinessEmail(businessDetails.getBusinessEmail());
//            e.setFax(businessDetails.getFax());
//            e.setStoreName(businessDetails.getStoreName());
//            return businessDetailsRepository.save(e);
//        }).orElse(()-> return businessDetailsRepository.save(businessDetails));;
        Optional<BusinessDetails> byBusinessEmail = businessDetailsRepository.findByBusinessEmail(businessDetails.getBusinessEmail());
    if (byBusinessEmail.isPresent()) {
        BusinessDetails businessDetails1 = byBusinessEmail.get();
        businessDetails1.setBusinessEmail(businessDetails.getBusinessEmail());
        businessDetails1.setStoreName(businessDetails.getStoreName());
        return businessDetailsRepository.save(businessDetails1);
    } else{
        return businessDetailsRepository.save(businessDetails);
    }
    }

    @Override
    public Optional<BusinessDetails> cancelBusinessDetails(BusinessDetails businessDetails) {
        return this.businessDetailsRepository.findByBusinessEmail(businessDetails.getBusinessEmail());
    }
}
