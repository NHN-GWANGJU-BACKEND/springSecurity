package com.nhnacademy.edu.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "birth_death_report_resident")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BirthDeathReportResident {
    @EmbeddedId
    private Pk pk;

    @MapsId("residentSerialNumber")
    @JoinColumn(name = "resident_serial_number")
    @ManyToOne
    private Resident targetResidentSerialNumber;

    @ManyToOne
    @JoinColumn(name ="report_resident_serial_number" )
    private Resident reportResidentSerialNumber;

    @Column(name = "birth_death_report_date")
    @Temporal(TemporalType.DATE)
    private Date birthDeathReportDate;

    @Column(name = "birth_report_qualifications_code")
    private String birthReportQualificationsCode;

    @Column(name = "death_report_qualifications_code")
    private String deathReportQualificationsCode;

    @Setter
    @Column(name = "email_address")
    private String emailAddress;

    @Setter
    @Column(name = "phone_number")
    private String phoneNumber;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "resident_serial_number")
        private Integer residentSerialNumber;

        @Column(name = "birth_death_type_code")
        private String birthDeathTypeCode;
    }
}
