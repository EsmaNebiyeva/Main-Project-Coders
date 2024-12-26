package org.example.project.service.other.impl;

import java.util.ArrayList;
import java.util.List;

import org.example.project.entity.other.Tablesss;
import org.example.project.model.TableDTO;
import org.example.project.repository.other.TablesRepository;

import org.example.project.service.other.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import  static org.example.project.model.TableDTO.conDto;
@Service
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class TableServiceImpl implements TablesService {
@Autowired
private TablesRepository tablesRepository;

@Override
public List<TableDTO> findTables() {
    List<Tablesss> all = tablesRepository.findAll();
    List<TableDTO> tabs=new ArrayList<>();
    for(Tablesss tab:all ){
       TableDTO conDto2 = conDto(tab);
       tabs.add(conDto2);
    }
    return tabs;
}

@Override
public Tablesss findByNumber(String number) {
    if(number!=null){
     Tablesss byNumber = tablesRepository.findByNumber(number);
     if(byNumber!=null){
        return byNumber;
     }
    } return null;
}
  
   
}