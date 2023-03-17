package com.nhnacademy.edu.controller.restapi;

import com.nhnacademy.edu.domain.HouseAddressModify;
import com.nhnacademy.edu.domain.HouseAddressRegister;
import com.nhnacademy.edu.entity.HouseholdMovementAddress;
import com.nhnacademy.edu.exception.NotDateTypeException;
import com.nhnacademy.edu.service.HouseAddressService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/household/{householdSerialNumber}/movement")
public class HouseAddressController {
    private final HouseAddressService houseAddressService;

    public HouseAddressController(HouseAddressService houseAddressService) {
        this.houseAddressService = houseAddressService;
    }

    @PostMapping
    public HouseholdMovementAddress register(@PathVariable("householdSerialNumber") int hhSerialNumber,
                                             @RequestBody HouseAddressRegister houseAddressRegister) {
        return houseAddressService.save(hhSerialNumber, houseAddressRegister);
    }

    @PutMapping("/{reportDate}")
    public HouseholdMovementAddress modify(@PathVariable("householdSerialNumber") int hhSerialNumber,
                                           @PathVariable("reportDate") String reportDate,
                                           @RequestBody HouseAddressModify houseAddressModify) {
        Date date;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            date = simpleDateFormat.parse(reportDate);
        } catch (ParseException e) {
            throw new NotDateTypeException();
        }

        HouseholdMovementAddress householdMovementAddress =
                houseAddressService.get(new HouseholdMovementAddress.Pk(date, hhSerialNumber));

        householdMovementAddress.setHouseMovementAddress(houseAddressModify.getHouseMovementAddress());

        return houseAddressService.modify(householdMovementAddress);
    }


    @DeleteMapping("/{reportDate}")
    public void delete(@PathVariable("householdSerialNumber") int hhSerialNumber,
                       @PathVariable("reportDate") String reportDate) {
        Date date;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            date = simpleDateFormat.parse(reportDate);
        } catch (ParseException e) {
            throw new NotDateTypeException();
        }

        HouseholdMovementAddress.Pk pk = new HouseholdMovementAddress.Pk(date, hhSerialNumber);

        houseAddressService.delete(pk);
    }
}
