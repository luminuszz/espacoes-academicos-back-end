package com.ea.backend.domain.notification.application.contracts;

public interface MailProvider {
    void sendEmail(String to, String subject, String body);
}
