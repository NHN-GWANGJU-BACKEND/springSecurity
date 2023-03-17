package com.nhnacademy.edu.repository.houseAddress;

import com.nhnacademy.edu.entity.HouseholdMovementAddress;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface HouseAddressRepositoryCustom {
    HouseholdMovementAddress findLastAddress();
}
