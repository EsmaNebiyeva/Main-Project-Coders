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
    public BusinessDetailsDTO saveBusinessDetails(String email,BusinessDetails businessDetails1) {
//        businessDetailsRepository.findByBusinessEmail(businessDetails.getBusinessEmail()).map(e -> {
//            e.setBusinessEmail(businessDetails.getBusinessEmail());
//            e.setFax(businessDetails.getFax());
//            e.setStoreName(businessDetails.getStoreName());
//            return businessDetailsRepository.save(e);
//        }).orElse(()-> return businessDetailsRepository.save(businessDetails));;

        Optional<BusinessDetails> byBusinessEmail = businessDetailsRepository.findByEmail(email);
    if (byBusinessEmail.isPresent()) {
        BusinessDetails businessDetails2 = byBusinessEmail.get();
        businessDetails2.setBusinessEmail(businessDetails1.getBusinessEmail());
        businessDetails2.setNumber(businessDetails1.getNumber());
        businessDetails2.setFax(businessDetails1.getFax());
        businessDetails2.setStoreName(businessDetails1.getStoreName());
        return convertToDto(businessDetails2);
    } else{
        BusinessDetails save1 = businessDetailsRepository.save(businessDetails1);
        return convertToDto(save1);
    }
    }

    @Override
    public boolean cancelBusinessDetails(BusinessDetails businessDetails) {

        return true;
    }
}
