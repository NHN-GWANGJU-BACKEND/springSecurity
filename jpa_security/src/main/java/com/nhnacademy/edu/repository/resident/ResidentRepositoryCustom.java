package com.nhnacademy.edu.repository.resident;

import com.nhnacademy.edu.domain.DTO.ResidentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ResidentRepositoryCustom {
    Page<ResidentDTO> findAllResident(Pageable pageable, Integer serialNumber);


}
