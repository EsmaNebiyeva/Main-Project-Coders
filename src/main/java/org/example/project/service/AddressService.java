package org.example.project.service;

import org.example.project.entity.Address;
import org.springframework.stereotype.Service;


public interface AddressService {
    Address saveAddress(Address address);
    Address deleteAddress(Address address);
}
