package com.nhnacademy.edu.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResidentDTO {
    private Integer serialNumber;
    private String name;
    private Boolean birth;
    private Boolean death;
}
