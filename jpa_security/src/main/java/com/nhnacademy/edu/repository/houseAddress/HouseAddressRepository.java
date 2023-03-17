package com.nhnacademy.edu.repository.houseAddress;

import com.nhnacademy.edu.entity.HouseholdMovementAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseAddressRepository
        extends JpaRepository<HouseholdMovementAddress, HouseholdMovementAddress.Pk>, HouseAddressRepositoryCustom {
}
