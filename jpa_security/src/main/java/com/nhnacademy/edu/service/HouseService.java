package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.HouseholdRegister;
import com.nhnacademy.edu.entity.Household;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.HouseholdNotFoundException;
import com.nhnacademy.edu.exception.ResidentNotFoundException;
import com.nhnacademy.edu.repository.household.HouseRepository;
import com.nhnacademy.edu.repository.resident.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;


@Service
@Transactional
public class HouseService {
    private final HouseRepository houseRepository;
    private final ResidentRepository residentRepository;

    public HouseService(HouseRepository houseRepository, ResidentRepository residentRepository) {
        this.houseRepository = houseRepository;
        this.residentRepository = residentRepository;
    }


    public Household register(HouseholdRegister householdRegister) {
        Optional<Resident> resident = residentRepository.findById(householdRegister.getHouseholdResidentSerialNumber());

        if (resident.isEmpty()) {
            throw new ResidentNotFoundException();
        }

        Household household = new Household(
                null,
                resident.get(),
                Date.valueOf(householdRegister.getHouseholdCompositionDate()),
                householdRegister.getHouseholdCompositionReasonCode(),
                householdRegister.getCurrentHouseMovementAddress(),
                new HashSet<>(),
                new HashSet<>());

        return houseRepository.save(household);
    }


    public void delete(Integer hhSerialNumber) {
        Optional<Household> household = houseRepository.findById(hhSerialNumber);

        if (household.isEmpty()) {
            throw new HouseholdNotFoundException();
        }

        houseRepository.delete(household.get());
    }

}
