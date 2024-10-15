package org.example.project.model;

import lombok.Data;

@Data
public class CategoryDTO {
    private Integer count;
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer countCategoryByName, String byName) {
        this.count = countCategoryByName;
        this.name = byName;
    }
}
