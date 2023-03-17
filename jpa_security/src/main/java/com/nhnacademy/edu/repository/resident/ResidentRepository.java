package com.nhnacademy.edu.repository.resident;

import com.nhnacademy.edu.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Integer>, ResidentRepositoryCustom {
    @Query("select count(h.householdSerialNumber) from Household h " +
            "inner join HouseholdCompositionResident hc " +
            "on h.householdSerialNumber = hc.pk.householdSerialNumber " +
            "where h.resident.residentSerialNumber = :serialNumber ")
    Long existFamilyFindByHouseHold(@Param("serialNumber") int serialNumber);

    Optional<Resident> findByLoginId(String username);

    Optional<Resident> findByEmail(String email);
}
