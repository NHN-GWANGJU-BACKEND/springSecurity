package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler(value = {
            BirthReportNotFoundException.class,
            FailedRemoveResidentInfoException.class,
            FamilyRelationshipNotFoundException.class,
            HouseholdAddressNotFoundException.class,
            HouseholdNotFoundException.class,
            NotDateTypeException.class,
            ResidentNotFoundException.class,
            Exception.class})
    public String handleValidationException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);

        return "error";
    }
}
