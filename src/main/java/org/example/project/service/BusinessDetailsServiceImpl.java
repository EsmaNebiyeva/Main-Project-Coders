package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.BusinessDetails;
import org.example.project.repository.BusinessDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessDetailsServiceImpl implements BusinessDetailsService{
    @Autowired
    private final BusinessDetailsRepository businessDetailsRepository;
    @Override
    public void saveBusinessDetails(BusinessDetails businessDetails) {
        businessDetailsRepository.save(businessDetails);
        System.out.println("DATA elave olundu");
    }
}
