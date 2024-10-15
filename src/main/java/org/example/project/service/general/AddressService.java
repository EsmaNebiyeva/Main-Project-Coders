package org.example.project.service.general;

import org.example.project.entity.general.Address;
import org.example.project.model.AddressDTO;

import java.util.Optional;


public interface AddressService {
    AddressDTO saveAddress(String email,Address address);

    boolean cancelAddress(Address address);
}
