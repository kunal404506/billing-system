package com.csi.controller;

import com.csi.model.EmailModel;
import com.csi.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Tag(name = "GST Billing", description = "APIS Of GST Bill Email Controller")
@SecurityRequirement(name = "Bearer Auth")
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendemail/{toEmailId}")
    public ResponseEntity<String> sendEmail(@PathVariable String toEmailId) {
        emailService.sendEmail(toEmailId);
        return new ResponseEntity<>("Email Sent Successfully", HttpStatus.OK);
    }
}
