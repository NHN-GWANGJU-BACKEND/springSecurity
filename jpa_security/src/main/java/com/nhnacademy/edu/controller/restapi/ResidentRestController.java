package com.nhnacademy.edu.controller.restapi;

import com.nhnacademy.edu.domain.ResidentModify;
import com.nhnacademy.edu.domain.ResidentRegister;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.ResidentNotFoundException;
import com.nhnacademy.edu.service.ResidentService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Objects;

@RestController
public class ResidentRestController {
    private final ResidentService residentService;

    public ResidentRestController(ResidentService residentService) {
        this.residentService = residentService;
    }


    @PostMapping("/api/residents")
    public Resident register(@RequestBody ResidentRegister residentRegister) {
        return residentService.save(residentRegister);
    }


    @PutMapping("/api/residents/{serialNumber}")
    public Resident modify(@RequestBody ResidentModify residentModify,
                           @PathVariable("serialNumber") Integer serialNumber) {

        Resident resident = residentService.getResident(serialNumber);

        if (Objects.isNull(resident)) {
            throw new ResidentNotFoundException();
        }

        resident.setDeathDate(residentModify.getDeathDate());
        resident.setDeathPlaceAddress(residentModify.getDeathPlaceAddress());
        resident.setName(residentModify.getName());
        resident.setDeathPlaceCode(residentModify.getDeathPlaceCode());
        resident.setRegistrationBaseAddress(residentModify.getRegistrationBaseAddress());
        resident.setEmail(residentModify.getEmail());

        return residentService.modify(resident);
    }


}
