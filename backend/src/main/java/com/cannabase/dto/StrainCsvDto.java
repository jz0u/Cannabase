package com.cannabase.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class StrainCsvDto {
    @CsvBindByName(column = "Strain")
    private String name;
    
    @CsvBindByName(column = "Type")
    private String type;
    
    @CsvBindByName(column = "Rating")
    private Double rating;
    
    @CsvBindByName(column = "Effects")
    private String effects;
    
    @CsvBindByName(column = "Flavor")
    private String flavor;
    
    @CsvBindByName(column = "Description")
    private String description;
}