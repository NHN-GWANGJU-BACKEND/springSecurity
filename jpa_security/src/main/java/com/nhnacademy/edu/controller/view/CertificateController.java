package com.nhnacademy.edu.controller.view;

import com.nhnacademy.edu.domain.DTO.CertificateDTO;
import com.nhnacademy.edu.service.CertificateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/certificates")
    public String certificateRegister(@RequestParam int serialNumber,
                                      @RequestParam String type) {
        certificateService.save(serialNumber, type);

        return "redirect:/residents";
    }

    @GetMapping("/certificates/{serialNumber}")
    public String certificateView(@PathVariable("serialNumber") int serialNumber,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  Model model) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<CertificateDTO> certificateDTOPage
                = certificateService.getAllCertificateBySerialNumber(pageable, serialNumber);

        List<CertificateDTO> certificates = certificateDTOPage.getContent();

        model.addAttribute("serialNumber", serialNumber);
        model.addAttribute("certificates", certificates);
        model.addAttribute("maxPage", 5);
        model.addAttribute("pages", certificateDTOPage);


        return "certificates";
    }
}
