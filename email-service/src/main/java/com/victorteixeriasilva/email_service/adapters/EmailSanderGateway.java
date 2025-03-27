package com.victorteixeriasilva.email_service.adapters;

public interface EmailSanderGateway {
    void sendEmail(String to, String subject, String body);
}
