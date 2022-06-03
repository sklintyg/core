package se.inera.intyg.cts.infrastructure.integration;

import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.TellusTalkResponseDTO;

public interface SendEmail {

    TellusTalkResponseDTO sendEmail(String emailAddress, String message, String subject);

}