package com.nhnacademy.edu.repository.certificate;

import com.nhnacademy.edu.domain.DTO.CertificateDTO;
import com.nhnacademy.edu.entity.CertificateIssue;
import com.nhnacademy.edu.entity.QCertificateIssue;
import com.nhnacademy.edu.entity.QResident;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CertificateRepositoryImpl extends QuerydslRepositorySupport implements CertificateRepositoryCustom {
    public CertificateRepositoryImpl() {
        super(CertificateIssue.class);
    }


    @Override
    public Page<CertificateDTO> findCertificatesBySerialNumber(Pageable pageable, int serialNumber) {
        QCertificateIssue certificate = QCertificateIssue.certificateIssue;
        QResident resident = QResident.resident;

        List<CertificateDTO> list = from(certificate)
                .innerJoin(resident)
                .on(resident.residentSerialNumber.eq(certificate.resident.residentSerialNumber))
                .where(certificate.resident.residentSerialNumber.eq(serialNumber))
                .select(Projections.constructor(CertificateDTO.class,
                        certificate.certificateConfirmationNumber,
                        resident.name,
                        certificate.certificateTypeCode,
                        certificate.certificateIssueDate
                ))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long totalSize = from(certificate)
                .innerJoin(resident)
                .on(resident.residentSerialNumber.eq(certificate.resident.residentSerialNumber))
                .where(certificate.resident.residentSerialNumber.eq(serialNumber))
                .select(certificate.count())
                .fetchOne();

        return new PageImpl<>(list, pageable, totalSize);
    }
}
