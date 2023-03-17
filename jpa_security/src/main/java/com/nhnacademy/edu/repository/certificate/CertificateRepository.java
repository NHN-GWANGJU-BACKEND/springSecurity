package com.nhnacademy.edu.repository.certificate;

import com.nhnacademy.edu.entity.CertificateIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<CertificateIssue,Long>, CertificateRepositoryCustom {


}
