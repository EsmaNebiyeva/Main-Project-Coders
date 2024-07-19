package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;
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
    public void saveAddress(Address address) {
        addressRepository.save(address);
        System.out.println("DATA elave olundu");
    }

    @Override
    public void deleteAddress(Address address) {
        addressRepository.delete(address);
        System.out.println("DATA silindi");
    }
}
