package com.nhnacademy.edu.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "household_movement_address")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdMovementAddress {
    @EmbeddedId
    private Pk pk;

    @MapsId("householdSerialNumber")
    @ManyToOne
    @JoinColumn(name = "household_serial_number")
    private Household household;

    @Setter
    @Column(name = "house_movement_address")
    private String houseMovementAddress;

    @Setter
    @Column(name = "last_address_yn")
    private char lastAddressYN;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "house_movement_report_date")
        private Date houseMovementReportDate;

        @Column(name = "household_serial_number")
        private Integer householdSerialNumber;
    }
}
