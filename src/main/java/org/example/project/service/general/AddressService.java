package org.example.project.service.general;

import org.example.project.entity.general.Address;

import java.util.Optional;


public interface AddressService {
    Address saveAddress(Address address);
    Optional<Address> cancelAddress(Address address);
}
