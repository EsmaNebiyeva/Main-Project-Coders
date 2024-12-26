package org.example.project.controller;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class IncomeDTO {
    private List<Double> incomes;
    private List<String> years;
    private Double monthlyIncome;
    private Integer products;
    private Integer purchase;
}
