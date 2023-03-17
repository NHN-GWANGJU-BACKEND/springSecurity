package com.nhnacademy.edu.controller.restapi;

import com.nhnacademy.edu.domain.BirthDeathModify;
import com.nhnacademy.edu.domain.BirthDeathRegister;
import com.nhnacademy.edu.entity.BirthDeathReportResident;
import com.nhnacademy.edu.exception.BirthReportNotFoundException;
import com.nhnacademy.edu.service.BirthDeathService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/residents/death")
public class DeathRestController {

    private final BirthDeathService birthDeathService;

    public DeathRestController(BirthDeathService birthDeathService) {
        this.birthDeathService = birthDeathService;
    }

    @PostMapping
    public BirthDeathReportResident register(@RequestBody BirthDeathRegister birthDeathRegister) {
        return birthDeathService.register(birthDeathRegister);
    }

    @PutMapping("/{serialNumber}")
    public BirthDeathReportResident modify(@PathVariable("serialNumber") int targetSerialNumber,
                                           @RequestBody BirthDeathModify birthDeathModify) {

        BirthDeathReportResident birthDeath = birthDeathService.getDeath(targetSerialNumber);

        if(Objects.isNull(birthDeath)){
            throw new BirthReportNotFoundException();
        }

        birthDeath.setEmailAddress(birthDeathModify.getEmailAddress());
        birthDeath.setPhoneNumber(birthDeath.getPhoneNumber());

        return birthDeathService.modify(birthDeath);
    }

    @DeleteMapping("/{serialNumber}")
    public void delete(@PathVariable("serialNumber") int targetSerialNumber){
        birthDeathService.deleteDeath(targetSerialNumber);
    }
}
