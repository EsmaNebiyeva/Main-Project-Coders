package org.example.project.service.general.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.general.BusinessDetails;
import org.example.project.model.BusinessDetailsDTO;
import org.example.project.repository.general.BusinessDetailsRepository;
import org.example.project.service.general.BusinessDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.project.model.BusinessDetailsDTO.convertToDto;
import static org.example.project.model.BusinessDetailsDTO.fromDTOToNormal;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessDetailsServiceImpl implements BusinessDetailsService {
    @Autowired
    private final BusinessDetailsRepository businessDetailsRepository;
    @Override
    public BusinessDetails saveBusinessDetails(BusinessDetailsDTO businessDetails) {
//        businessDetailsRepository.findByBusinessEmail(businessDetails.getBusinessEmail()).map(e -> {
//            e.setBusinessEmail(businessDetails.getBusinessEmail());
//            e.setFax(businessDetails.getFax());
//            e.setStoreName(businessDetails.getStoreName());
//            return businessDetailsRepository.save(e);
//        }).orElse(()-> return businessDetailsRepository.save(businessDetails));;
        BusinessDetails businessDetails1 = fromDTOToNormal(businessDetails);
        Optional<BusinessDetails> byBusinessEmail = businessDetailsRepository.findByBusinessEmail(businessDetails1.getBusinessEmail());
    if (byBusinessEmail.isPresent()) {
        BusinessDetails businessDetails2 = byBusinessEmail.get();
        businessDetails1.setBusinessEmail(businessDetails.getBusinessEmail());
        businessDetails1.setStoreName(businessDetails.getStoreName());
        return businessDetailsRepository.save(businessDetails2);
    } else{
        return businessDetailsRepository.save(businessDetails1);
    }
    }

    @Override
    public Optional<BusinessDetails> cancelBusinessDetails(BusinessDetailsDTO businessDetails) {

        return this.businessDetailsRepository.findByBusinessEmail(businessDetails.getBusinessEmail());
    }
}
