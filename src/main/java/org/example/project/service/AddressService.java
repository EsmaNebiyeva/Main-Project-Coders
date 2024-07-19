package org.example.project.service;

import org.example.project.entity.Address;
import org.springframework.stereotype.Service;


public interface AddressService {
    void saveAddress(Address address);
    void deleteAddress(Address address);
}
