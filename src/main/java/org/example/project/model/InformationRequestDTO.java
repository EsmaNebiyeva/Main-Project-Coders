package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class InformationRequestDTO {
    private BusinessDetailsDTO businessDetails;
    private AddressDTO address;
}
