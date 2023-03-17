package com.nhnacademy.edu.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "resident")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resident {
    @Id
    @Column(name = "resident_serial_number")
    private Integer residentSerialNumber;

    @Setter
    private String name;

    @Column(name = "resident_registration_number")
    private String residentRegistrationNumber;

    @Column(name = "gender_code")
    private String genderCode;

    @Column(name = "birth_date")
    private Timestamp birthDate;

    @Column(name = "birth_place_code")
    private String birthPlaceCode;

    @Setter
    @Column(name = "registration_base_address")
    private String registrationBaseAddress;

    @Setter
    @Column(name = "death_date")
    private Timestamp deathDate;

    @Setter
    @Column(name = "death_place_code")
    private String deathPlaceCode;

    @Setter
    @Column(name = "death_place_address")
    private String deathPlaceAddress;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "login_pwd")
    private String password;

    @Setter
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "targetResidentSerialNumber", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    List<BirthDeathReportResident> birthDeathReport;

    @JsonIgnore
    @OneToMany(mappedBy = "resident",cascade = CascadeType.REMOVE)
    private List<FamilyRelationship> familyRelationship;

    @JsonIgnore
    @OneToMany(mappedBy = "resident", cascade = CascadeType.REMOVE)
    private List<CertificateIssue> certificateIssues;

    @JsonIgnore
    @OneToMany(mappedBy = "resident", cascade = CascadeType.REMOVE)
    private List<Household> households;

    @JsonIgnore
    @OneToMany(mappedBy = "resident", cascade = CascadeType.REMOVE)
    private List<HouseholdCompositionResident> householdCompositionResidents;
}
