package com.nhnacademy.edu.controller.restapi;

import com.nhnacademy.edu.domain.HouseholdRegister;
import com.nhnacademy.edu.entity.Household;
import com.nhnacademy.edu.service.HouseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/household")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @PostMapping
    public Household register(@RequestBody HouseholdRegister householdRegister) {
        return houseService.register(householdRegister);
    }

    @DeleteMapping("{householdSerialNumber}")
    public void delete(@PathVariable("householdSerialNumber") int hhSerialNumber) {
        houseService.delete(hhSerialNumber);
    }

}
