package com.nhnacademy.edu.domain;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ResidentRegister {
    private Integer residentSerialNumber;

    private String name;

    private String registrationNumber;

    private String genderCode;

    private Timestamp birthDate;

    private String birthPlaceCode;

    private String registrationBaseAddress;

    private Timestamp deathDate;

    private String deathPlaceCode;

    private String deathPlaceAddress;

    private String loginId;

    private String pwd;
}
