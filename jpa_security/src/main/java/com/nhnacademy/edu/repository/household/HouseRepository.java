package com.nhnacademy.edu.repository.household;

import com.nhnacademy.edu.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseRepository extends JpaRepository<Household, Integer> {
    @Query("select h from Household h join fetch h.hhcrs join fetch h.hhmas where h.householdSerialNumber = :serialNumber")
    Household findHouseholdWithJoin(@Param("serialNumber") int serialNumber);

}
