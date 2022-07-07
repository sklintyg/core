package se.inera.intyg.cts.infrastructure.integration;

import javax.mail.MessagingException;

public interface SendEmail {

    void sendEmail(String emailAddress, String emailBody, String emailSubject)
        throws MessagingException;
}