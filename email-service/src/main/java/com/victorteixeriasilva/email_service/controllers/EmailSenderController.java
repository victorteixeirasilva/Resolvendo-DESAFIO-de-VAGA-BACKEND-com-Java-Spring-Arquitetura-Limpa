package com.victorteixeriasilva.email_service.controllers;

import com.victorteixeriasilva.email_service.aplication.EmailSanderService;
import com.victorteixeriasilva.email_service.core.EmailRequest;
import com.victorteixeriasilva.email_service.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailSenderController {

    private final EmailSanderService emailSanderService;

    @Autowired
    public EmailSenderController(EmailSanderService emailSanderService){
        this.emailSanderService = emailSanderService;
    }

    @PostMapping()
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request){
        try {
            this.emailSanderService.sendEmail(request.to(), request.subject(), request.body());
            return ResponseEntity.ok("email send sucessfully");
        } catch (EmailServiceException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending email");
        }
    }
}
