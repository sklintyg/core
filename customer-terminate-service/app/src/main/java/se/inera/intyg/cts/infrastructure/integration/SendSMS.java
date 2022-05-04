package se.inera.intyg.cts.infrastructure.integration;

import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.SMSResponseDTO;

public interface SendSMS {

    SMSResponseDTO sendSMS(String phonenumber, String message);

}