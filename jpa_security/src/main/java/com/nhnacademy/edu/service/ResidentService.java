package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.DTO.ResidentDTO;
import com.nhnacademy.edu.domain.ResidentRegister;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.FailedRemoveResidentInfoException;
import com.nhnacademy.edu.exception.ResidentNotFoundException;
import com.nhnacademy.edu.repository.resident.ResidentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResidentService {
    private final ResidentRepository residentRepository;
    private final PasswordEncoder passwordEncoder;

    public ResidentService(ResidentRepository residentRepository, PasswordEncoder passwordEncoder) {
        this.residentRepository = residentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Resident save(ResidentRegister residentRegister) {
        Resident resident = Resident.builder()
                .residentSerialNumber(residentRegister.getResidentSerialNumber())
                .name(residentRegister.getName())
                .residentRegistrationNumber(residentRegister.getRegistrationNumber())
                .genderCode(residentRegister.getGenderCode())
                .birthDate(residentRegister.getBirthDate())
                .birthPlaceCode(residentRegister.getBirthPlaceCode())
                .registrationBaseAddress(residentRegister.getRegistrationBaseAddress())
                .deathDate(residentRegister.getDeathDate())
                .deathPlaceCode(residentRegister.getDeathPlaceCode())
                .deathPlaceAddress(residentRegister.getDeathPlaceAddress())
                .loginId(residentRegister.getLoginId())
                .password(passwordEncoder.encode(residentRegister.getPwd()))
                .build();

        return residentRepository.save(resident);
    }

    public Resident getResident(Integer serialNumber) {
        return residentRepository.findById(serialNumber).orElse(null);
    }

    public Resident modify(Resident resident) {
        return residentRepository.save(resident);
    }

    public void delete(int serialNumber) {
        Long familyCount = residentRepository.existFamilyFindByHouseHold(serialNumber);

        if (familyCount > 1) {
            throw new FailedRemoveResidentInfoException("남은 가족이 있어 삭제가 불가능합니다");
        }

        residentRepository.deleteById(serialNumber);
    }

    public Page<ResidentDTO> findAll(Pageable pageable, String loginId) {
        Resident resident = residentRepository.findByLoginId(loginId)
                .orElseThrow(ResidentNotFoundException::new);

        return residentRepository.findAllResident(pageable, resident.getResidentSerialNumber());
    }
}
