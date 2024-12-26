package org.example.project.service.other;

import java.util.List;

import org.example.project.entity.other.Tablesss;
import org.example.project.model.TableDTO;

public interface TablesService {
    List<TableDTO> findTables();
    Tablesss findByNumber(String number);
}
