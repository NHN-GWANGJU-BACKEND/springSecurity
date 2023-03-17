package com.nhnacademy.edu.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CertificateDTO {
    private Long id;
    private String name;
    private String typeCode;
    private LocalDateTime date;
}
