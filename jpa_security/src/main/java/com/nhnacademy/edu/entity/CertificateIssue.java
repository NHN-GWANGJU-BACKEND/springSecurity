package com.nhnacademy.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificate_issue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CertificateIssue {
    @Id
    @Column(name = "certificate_confirmation_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateConfirmationNumber;

    @JoinColumn(name = "resident_serial_number")
    @ManyToOne
    private Resident resident;

    @Column(name = "certificate_type_code")
    private String certificateTypeCode;

    @Column(name = "certificate_issue_date")
    private LocalDateTime certificateIssueDate;
}
