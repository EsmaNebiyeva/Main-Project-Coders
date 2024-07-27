package org.example.project.service;

import org.example.project.entity.Address;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface AddressService {
    Address saveAddress(Address address);
    Optional<Address> cancelAddress(Address address);
}
