package org.example.project.service.general.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.general.Address;
import org.example.project.model.AddressDTO;
import org.example.project.repository.general.AddressRepository;
import org.example.project.service.general.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static org.example.project.model.AddressDTO.convertToDto;
import static org.example.project.model.AddressDTO.fromDTOToNormal;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private final AddressRepository addressRepository;

    @Override
    public AddressDTO saveAddress(String email,Address address) {
        Optional<Address> addressOptional = addressRepository.findByEmail(email);
            if (addressOptional.isPresent()) {
                Address address1 = addressOptional.get();
                address1.setCity(address.getCity());
                address1.setFlat(address.getFlat());
                address1.setCountry(address.getCountry());
                address1.setPostalCode(address.getPostalCode());
                address1.setStreet(address.getStreet());
                address1.setStreetNumber(address.getStreetNumber());
                return convertToDto(address1);
            } else {
                Address save1 = addressRepository.save(address);
                return convertToDto(save1);
            }
    }


        @Override
        public boolean cancelAddress (Address address){
            return true;
        }
    }

