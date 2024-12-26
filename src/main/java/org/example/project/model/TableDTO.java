package org.example.project.model;



import org.example.project.entity.other.Tablesss;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TableDTO {
    private String number;
    private String access;
    public static TableDTO conDto(Tablesss table){
        TableDTO tableDTO=new TableDTO();
        tableDTO.setAccess(table.getAccess().name());
        tableDTO.setNumber(table.getNumber());
        return tableDTO;
    }
}
