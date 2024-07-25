package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;
import org.example.project.exception.OurException;
import org.example.project.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private final AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        if (address!= null) {
            addressRepository.save(address);
            System.out.println("DATA elave olundu");
            return address;
        }
        throw  new  OurException("address nulldu");
    }

    @Override
    public Address deleteAddress(Address address) {
        if (address != null) {
            addressRepository.delete(address);
            System.out.println("DATA silindi");
            return address;
        }
        throw  new  OurException("address nulldu");
    }
}
