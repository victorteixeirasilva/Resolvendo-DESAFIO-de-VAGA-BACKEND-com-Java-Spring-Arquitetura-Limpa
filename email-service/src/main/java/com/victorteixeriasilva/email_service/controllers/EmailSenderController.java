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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Email Sander", description = "Gerenciamento de Envio de Emails")
@RestController
@RequestMapping("/api/email")
public class EmailSenderController {

    private final EmailSanderService emailSanderService;

    @Autowired
    public EmailSenderController(EmailSanderService emailSanderService){
        this.emailSanderService = emailSanderService;
    }

    @Operation(
            summary =
                    "Enviando um email",
            description =
                    "Retorna uma mensagem informando se o e-mail foi enviado ou não com sucesso!")
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
