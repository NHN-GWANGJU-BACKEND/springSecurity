package com.nhnacademy.edu.controller.view;

import com.nhnacademy.edu.domain.DTO.ResidentDTO;
import com.nhnacademy.edu.service.ResidentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/residents")
public class ResidentViewController {
    private final ResidentService residentService;

    public ResidentViewController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @GetMapping
    public String mainUserList(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model) {
        PageRequest pageable = PageRequest.of(page, size);

        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails principal = (UserDetails) context.getAuthentication().getPrincipal();

        Page<ResidentDTO> residentDTOPage = residentService.findAll(pageable, principal.getUsername());

        List<ResidentDTO> residents = residentDTOPage.getContent();


        model.addAttribute("residents", residents);
        model.addAttribute("pages", residentDTOPage);
        model.addAttribute("maxPage", 5);

        return "residents";
    }

    @PostMapping("{serialNumber}")
    public String removeResident(@PathVariable("serialNumber") int serialNumber) {

        residentService.delete(serialNumber);

        return "redirect:/residents";
    }

}
