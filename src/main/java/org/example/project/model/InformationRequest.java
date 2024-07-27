package org.example.project.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.project.entity.Address;
import org.example.project.entity.BusinessDetails;

@Data
@RequiredArgsConstructor
public class InformationRequest {
    private BusinessDetails businessDetails;
    private Address address;
}
