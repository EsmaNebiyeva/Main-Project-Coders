package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.project.entity.general.Address;
import org.example.project.entity.general.BusinessDetails;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class InformationRequestDTO {
    private BusinessDetailsDTO businessDetails;
    private AddressDTO address;
}
