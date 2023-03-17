package com.nhnacademy.edu.repository.resident;

import com.nhnacademy.edu.domain.DTO.ResidentDTO;
import com.nhnacademy.edu.entity.*;
import com.nhnacademy.edu.exception.HouseholdNotFoundException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResidentRepositoryImpl extends QuerydslRepositorySupport implements ResidentRepositoryCustom {

    public ResidentRepositoryImpl() {
        super(Resident.class);
    }


    @Override
    public Page<ResidentDTO> findAllResident(Pageable pageable, Integer serialNumber) {
        QResident resident = QResident.resident;
        QHousehold household = QHousehold.household;
        QHouseholdCompositionResident compositionResident = QHouseholdCompositionResident.householdCompositionResident;
        QResident resident2 = QResident.resident;
        QBirthDeathReportResident birthDeath = QBirthDeathReportResident.birthDeathReportResident;

        Integer householdSerialNumber = from(household)
                .select(household.householdSerialNumber)
                .where(household.resident.residentSerialNumber.eq(serialNumber))
                .fetchOne();

        if(Objects.isNull(householdSerialNumber)){
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        List<ResidentDTO> list = from(household)
                .leftJoin(compositionResident)
                .on(compositionResident.household.householdSerialNumber.eq(householdSerialNumber))
                .innerJoin(resident)
                .on(compositionResident.resident.residentSerialNumber.eq(resident.residentSerialNumber))
                .leftJoin(birthDeath)
                .on(birthDeath.pk.residentSerialNumber.eq(resident2.residentSerialNumber))
                .select(Projections.constructor(ResidentDTO.class,
                        resident.residentSerialNumber,
                        resident.name,
                        birthDeath.pk.birthDeathTypeCode.isNotNull(),
                        resident.deathDate.isNotNull()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = from(household)
                .leftJoin(compositionResident)
                .on(compositionResident.household.householdSerialNumber.eq(householdSerialNumber))
                .innerJoin(resident)
                .on(compositionResident.resident.residentSerialNumber.eq(resident.residentSerialNumber))
                .leftJoin(birthDeath)
                .on(birthDeath.pk.residentSerialNumber.eq(resident2.residentSerialNumber))
                .select(household.count())
                .fetchOne();


        return new PageImpl<>(list, pageable, count);
    }
}
