package com.nhnacademy.edu.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "household")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "household_serial_number")
    private Integer householdSerialNumber;

    @JoinColumn(name = "household_resident_serial_number")
    @ManyToOne
    private Resident resident;

    @Column(name = "household_composition_date")
    @Temporal(TemporalType.DATE)
    private Date householdCompositionDate;

    @Column(name = "household_composition_reason_code")
    private String householdCompositionReasonCode;

    @Column(name = "current_house_movement_address")
    private String currentHouseMovementAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "household", cascade = CascadeType.REMOVE)
    Set<HouseholdCompositionResident> hhcrs;

    @JsonIgnore
    @OneToMany(mappedBy = "household", cascade = CascadeType.REMOVE)
    Set<HouseholdMovementAddress> hhmas;
}
