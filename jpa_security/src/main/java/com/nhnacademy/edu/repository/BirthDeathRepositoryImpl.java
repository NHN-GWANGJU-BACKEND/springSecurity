package com.nhnacademy.edu.repository;

import com.nhnacademy.edu.entity.BirthDeathReportResident;
import com.nhnacademy.edu.entity.QBirthDeathReportResident;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BirthDeathRepositoryImpl extends QuerydslRepositorySupport implements BirthDeathRepositoryCustom{
    public BirthDeathRepositoryImpl() {
        super(BirthDeathReportResident.class);
    }

    @Override
    public BirthDeathReportResident getBirth(int targetSerialNumber) {
        QBirthDeathReportResident birthDeathReportResident = QBirthDeathReportResident.birthDeathReportResident;

        return from(birthDeathReportResident)
                .where(birthDeathReportResident.pk.birthDeathTypeCode.eq("출생"))
                .where(birthDeathReportResident.pk.residentSerialNumber.eq(targetSerialNumber))
                .fetchOne();
    }

    @Override
    public BirthDeathReportResident getDeath(int targetSerialNumber) {
        QBirthDeathReportResident birthDeathReportResident = QBirthDeathReportResident.birthDeathReportResident;

        return from(birthDeathReportResident)
                .where(birthDeathReportResident.pk.birthDeathTypeCode.eq("사망"))
                .where(birthDeathReportResident.pk.residentSerialNumber.eq(targetSerialNumber))
                .fetchOne();
    }
}
