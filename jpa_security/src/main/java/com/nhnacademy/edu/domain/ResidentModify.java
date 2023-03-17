package com.nhnacademy.edu.domain;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ResidentModify {
    private String name;
    private String registrationBaseAddress;
    private Timestamp deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;
    private String email;
}
