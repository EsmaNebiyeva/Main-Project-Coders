package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;

import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String country;
    private String city;
    private String street;
    private String flat;
    private String streetNumber;
    private String postalCode;
    private List<UserDTO> users;


    public static AddressDTO convertToDto(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setCountry(address.getCountry());
        dto.setCity(address.getCity());
        dto.setStreet(address.getStreet());
        dto.setFlat(address.getFlat());
        dto.setStreetNumber(address.getStreetNumber());
        dto.setPostalCode(address.getPostalCode());
        return dto;
    }


    public static Address fromDTOToNormal(AddressDTO dto) {
        Address address = new Address();
        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setFlat(dto.getFlat());
        address.setStreetNumber(dto.getStreetNumber());
        address.setPostalCode(dto.getPostalCode());
        return address;
    }
}
