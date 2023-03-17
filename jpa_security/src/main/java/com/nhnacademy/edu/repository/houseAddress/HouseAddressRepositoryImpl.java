package com.nhnacademy.edu.repository.houseAddress;

import com.nhnacademy.edu.entity.HouseholdMovementAddress;
import com.nhnacademy.edu.entity.QHouseholdMovementAddress;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class HouseAddressRepositoryImpl extends QuerydslRepositorySupport implements HouseAddressRepositoryCustom{

    public HouseAddressRepositoryImpl() {
        super(HouseholdMovementAddress.class);
    }

    @Override
    public HouseholdMovementAddress findLastAddress() {
        QHouseholdMovementAddress householdMovementAddress = QHouseholdMovementAddress.householdMovementAddress;

        return from(householdMovementAddress)
                .orderBy(householdMovementAddress.pk.houseMovementReportDate.desc())
                .limit(1)
                .fetchOne();
    }
}
