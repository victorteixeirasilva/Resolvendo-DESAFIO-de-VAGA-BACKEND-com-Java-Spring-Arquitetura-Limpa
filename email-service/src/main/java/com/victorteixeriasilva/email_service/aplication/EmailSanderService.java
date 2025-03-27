package com.victorteixeriasilva.email_service.aplication;

import com.victorteixeriasilva.email_service.adapters.EmailSanderGateway;
import com.victorteixeriasilva.email_service.core.EmailSanderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSanderService implements EmailSanderUseCase {

    private final EmailSanderGateway emailSanderGateway;

    @Autowired
    public EmailSanderService(EmailSanderGateway gateway){
        this.emailSanderGateway = gateway;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        this.emailSanderGateway.sendEmail(to, subject, body);
    }

}
