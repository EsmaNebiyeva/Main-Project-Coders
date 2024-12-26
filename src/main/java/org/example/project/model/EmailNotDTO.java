package org.example.project.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmailNotDTO {
    private String email;
    private List<String> nots;
    
    @Override
    public String toString() {
        return "EmailNotDTO{" +
                "email='" + email + '\'' +
                ", nots=" + (nots != null ? nots : "[]") +
                '}';
    }
}
