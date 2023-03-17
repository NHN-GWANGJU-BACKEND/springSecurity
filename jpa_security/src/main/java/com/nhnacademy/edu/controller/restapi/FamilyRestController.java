package com.nhnacademy.edu.controller.restapi;

import com.nhnacademy.edu.domain.FamilyModify;
import com.nhnacademy.edu.domain.FamilyRegister;
import com.nhnacademy.edu.entity.FamilyRelationship;
import com.nhnacademy.edu.exception.FamilyRelationshipNotFoundException;
import com.nhnacademy.edu.service.FamilyService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/residents/{serialNumber}/relationship")
public class FamilyRestController {
    private final FamilyService familyService;

    public FamilyRestController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @PostMapping
    public FamilyRelationship register(@PathVariable("serialNumber") int serialNumber,
                                       @RequestBody FamilyRegister familyRegister) {
        return familyService.save(serialNumber,familyRegister);
    }

    @PutMapping("/{familySerialNumber}")
    public FamilyRelationship modify(@PathVariable("serialNumber") int serialNumber,
                                     @PathVariable("familySerialNumber") int familySerialNumber,
                                     @RequestBody FamilyModify familyModify) {
        FamilyRelationship familyRelationship =
                familyService.getFamilyRelationship(new FamilyRelationship.Pk(familySerialNumber, serialNumber));

        if (Objects.isNull(familyRelationship)) {
            throw new FamilyRelationshipNotFoundException();
        }

        familyRelationship.setFamilyRelationshipCode(familyModify.getRelationShip());

        return familyService.modify(familyRelationship);
    }


    @DeleteMapping("/{familySerialNumber}")
    public void delete(@PathVariable("serialNumber") int serialNumber,
                       @PathVariable("familySerialNumber") int familySerialNumber) {
        familyService.delete(new FamilyRelationship.Pk(familySerialNumber, serialNumber));
    }

}
