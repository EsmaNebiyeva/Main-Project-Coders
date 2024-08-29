package org.example.project.service.general.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.general.Address;
import org.example.project.repository.general.AddressRepository;
import org.example.project.service.general.AddressService;
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
                address1.setCountry(address.getCountry());
                address1.setPostalCode(address.getPostalCode());
                address1.setStreet(address.getStreet());
                address1.setStreetNumber(address.getStreetNumber());
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

