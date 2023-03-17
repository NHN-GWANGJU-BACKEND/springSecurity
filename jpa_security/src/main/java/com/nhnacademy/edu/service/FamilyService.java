package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.FamilyRegister;
import com.nhnacademy.edu.entity.FamilyRelationship;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.FamilyRelationshipNotFoundException;
import com.nhnacademy.edu.exception.ResidentNotFoundException;
import com.nhnacademy.edu.repository.FamilyRepository;
import com.nhnacademy.edu.repository.resident.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final ResidentRepository residentRepository;

    public FamilyService(FamilyRepository familyRepository, ResidentRepository residentRepository) {
        this.familyRepository = familyRepository;
        this.residentRepository = residentRepository;
    }

    public FamilyRelationship save(int serialNumber, FamilyRegister familyRegister) {
        Optional<Resident> resident = residentRepository.findById(serialNumber);

        if(resident.isEmpty()){
            throw new ResidentNotFoundException();
        }

        FamilyRelationship familyRelationship =
                new FamilyRelationship(
                        new FamilyRelationship.Pk(familyRegister.getFamilySerialNumber(), serialNumber),
                        resident.get(),
                        familyRegister.getRelationShip());

        return familyRepository.save(familyRelationship);
    }

    public FamilyRelationship modify(FamilyRelationship familyRelationship) {
        return familyRepository.save(familyRelationship);
    }

    public void delete(FamilyRelationship.Pk pk) {
        Optional<FamilyRelationship> family = familyRepository.findById(pk);

        if (family.isEmpty()) {
            throw new FamilyRelationshipNotFoundException();
        }

        familyRepository.delete(family.get());
    }

    public FamilyRelationship getFamilyRelationship(FamilyRelationship.Pk pk) {
        return familyRepository.findById(pk).orElse(null);
    }
}
