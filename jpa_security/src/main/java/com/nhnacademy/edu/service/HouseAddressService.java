package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.HouseAddressRegister;
import com.nhnacademy.edu.entity.Household;
import com.nhnacademy.edu.entity.HouseholdMovementAddress;
import com.nhnacademy.edu.exception.HouseholdAddressNotFoundException;
import com.nhnacademy.edu.exception.HouseholdNotFoundException;
import com.nhnacademy.edu.repository.houseAddress.HouseAddressRepository;
import com.nhnacademy.edu.repository.household.HouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class HouseAddressService {
    private final HouseRepository houseRepository;
    private final HouseAddressRepository houseAddressRepository;

    public HouseAddressService(HouseRepository houseRepository, HouseAddressRepository houseAddressRepository) {
        this.houseRepository = houseRepository;
        this.houseAddressRepository = houseAddressRepository;
    }


    public HouseholdMovementAddress get(HouseholdMovementAddress.Pk pk) {
        Optional<HouseholdMovementAddress> householdMovementAddress = houseAddressRepository.findById(pk);

        if (householdMovementAddress.isEmpty()) {
            throw new HouseholdAddressNotFoundException();
        }

        return householdMovementAddress.get();
    }


    public HouseholdMovementAddress save(int hhSerialNumber, HouseAddressRegister houseAddressRegister) {
        Household householdWithJoin = houseRepository.findHouseholdWithJoin(hhSerialNumber);

        if (Objects.isNull(householdWithJoin)) {
            throw new HouseholdNotFoundException();
        }

        HouseholdMovementAddress.Pk pk =
                new HouseholdMovementAddress.Pk(Date.valueOf(houseAddressRegister.getHouseMovementReportDate()),
                        hhSerialNumber);

        HouseholdMovementAddress houseAddress = new HouseholdMovementAddress(
                pk,
                householdWithJoin,
                houseAddressRegister.getHouseMovementAddress(),
                'Y');

        lastAddressUpdate();

        return houseAddressRepository.save(houseAddress);
    }


    public HouseholdMovementAddress modify(HouseholdMovementAddress householdMovementAddress) {
        return houseAddressRepository.save(householdMovementAddress);
    }

    public void lastAddressUpdate() {
        HouseholdMovementAddress lastAddress = houseAddressRepository.findLastAddress();

        if (Objects.isNull(lastAddress)) {
            throw new HouseholdAddressNotFoundException();
        }

        lastAddress.setLastAddressYN('N');

        houseAddressRepository.saveAndFlush(lastAddress);
    }

    public void delete(HouseholdMovementAddress.Pk pk) {
        Optional<HouseholdMovementAddress> householdMovementAddress = houseAddressRepository.findById(pk);

        if (householdMovementAddress.isEmpty()) {
            throw new HouseholdAddressNotFoundException();
        }

        houseAddressRepository.delete(householdMovementAddress.get());
    }
}
