package com.nhnacademy.edu.domain;

import lombok.Getter;

@Getter
public class BirthDeathRegister {
    private Integer residentSerialNumber;
    private String birthDeathTypeCode;
    private int reportResidentSerialNumber;
    private String birthDeathReportDate;
    private String birthReportQualificationsCode;
    private String deathReportQualificationsCode;
    private String emailAddress;
    private String phoneNumber;
}
