package org.example.project.service.general;

import org.example.project.entity.general.Address;
import org.example.project.model.AddressDTO;




public interface AddressService {
    AddressDTO saveAddress(String email,Address address);
AddressDTO findAddress(String email);
    boolean cancelAddress(Address address);
}
