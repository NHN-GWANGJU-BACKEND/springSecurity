package com.nhnacademy.edu.service;

import com.nhnacademy.edu.domain.DTO.CertificateDTO;
import com.nhnacademy.edu.entity.CertificateIssue;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.ResidentNotFoundException;
import com.nhnacademy.edu.repository.certificate.CertificateRepository;
import com.nhnacademy.edu.repository.resident.ResidentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final ResidentRepository residentRepository;

    public CertificateService(CertificateRepository certificateRepository, ResidentRepository residentRepository) {
        this.certificateRepository = certificateRepository;
        this.residentRepository = residentRepository;
    }

    public CertificateIssue save(int serialNumber, String type) {
        Optional<Resident> resident = residentRepository.findById(serialNumber);

        if (resident.isEmpty()) {
            throw new ResidentNotFoundException();
        }

        return certificateRepository.save(new CertificateIssue(
                null,
                resident.get(),
                type,
                LocalDateTime.now()));
    }

    public Page<CertificateDTO> getAllCertificateBySerialNumber(Pageable pageable, int serialNumber){
        return certificateRepository.findCertificatesBySerialNumber(pageable,serialNumber);
    }
}
