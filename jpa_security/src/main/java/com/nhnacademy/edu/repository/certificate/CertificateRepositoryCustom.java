package com.nhnacademy.edu.repository.certificate;

import com.nhnacademy.edu.domain.DTO.CertificateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CertificateRepositoryCustom {

    Page<CertificateDTO> findCertificatesBySerialNumber(Pageable pageable, int serialNumber);
}
