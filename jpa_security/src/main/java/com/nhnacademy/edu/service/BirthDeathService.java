package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.BirthDeathRegister;
import com.nhnacademy.edu.entity.BirthDeathReportResident;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.BirthReportNotFoundException;
import com.nhnacademy.edu.exception.ResidentNotFoundException;
import com.nhnacademy.edu.repository.BirthDeathRepository;
import com.nhnacademy.edu.repository.resident.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BirthDeathService {
    private final BirthDeathRepository birthDeathRepository;
    private final ResidentRepository residentRepository;

    public BirthDeathService(BirthDeathRepository birthDeathRepository, ResidentRepository residentRepository) {
        this.birthDeathRepository = birthDeathRepository;
        this.residentRepository = residentRepository;
    }


    public BirthDeathReportResident register(BirthDeathRegister birthDeathRegister) {
        Optional<Resident> reportResident = residentRepository.findById(birthDeathRegister.getReportResidentSerialNumber());
        Optional<Resident> targetResident = residentRepository.findById(birthDeathRegister.getResidentSerialNumber());

        if(reportResident.isEmpty() || targetResident.isEmpty()){
            throw new ResidentNotFoundException();
        }

        BirthDeathReportResident birthDeathReportResident = new BirthDeathReportResident(
                new BirthDeathReportResident.Pk(
                        birthDeathRegister.getResidentSerialNumber(),
                        birthDeathRegister.getBirthDeathTypeCode()
                ),
                targetResident.get(),
                reportResident.get(),
                Date.valueOf(birthDeathRegister.getBirthDeathReportDate()),
                birthDeathRegister.getBirthReportQualificationsCode(),
                birthDeathRegister.getDeathReportQualificationsCode(),
                birthDeathRegister.getEmailAddress(),
                birthDeathRegister.getPhoneNumber()
        );

        return birthDeathRepository.save(birthDeathReportResident);
    }

    public BirthDeathReportResident modify(BirthDeathReportResident birthDeathReportResident) {
        return birthDeathRepository.save(birthDeathReportResident);
    }

    public void deleteBirth(int targetSerialNumber) {
        BirthDeathReportResident birth = getBirth(targetSerialNumber);

        if(Objects.isNull(birth)){
            throw new BirthReportNotFoundException();
        }

        birthDeathRepository.delete(birth);
    }

    public void deleteDeath(int targetSerialNumber) {
        BirthDeathReportResident birth = getDeath(targetSerialNumber);

        if(Objects.isNull(birth)){
            throw new BirthReportNotFoundException();
        }

        birthDeathRepository.delete(birth);
    }

    public BirthDeathReportResident getBirth(int targetSerialNumber) {
        return birthDeathRepository.getBirth(targetSerialNumber);
    }

    public BirthDeathReportResident getDeath(int targetSerialNumber) {
        return birthDeathRepository.getDeath(targetSerialNumber);
    }
}
