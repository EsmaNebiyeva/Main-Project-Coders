package org.example.project.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductCount {
    private ProductDTO productDTO;
    private Integer count;
}
