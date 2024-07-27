package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;
import org.example.project.entity.BusinessDetails;
import org.example.project.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private final AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
            Optional<Address> addressOptional = addressRepository.findByCity(address.getCity());
            if (addressOptional.isPresent()) {
                Address address1 = addressOptional.get();
                address1.setCity(address.getCity());
                address1.setFlat(address.getFlat());
                return addressRepository.save(address1);
            } else {
                return addressRepository.save(address);
            }
    }


        @Override
        public Optional<Address> cancelAddress (Address address){
            return this.addressRepository.findByCity(address.getCity());
        }
    }

