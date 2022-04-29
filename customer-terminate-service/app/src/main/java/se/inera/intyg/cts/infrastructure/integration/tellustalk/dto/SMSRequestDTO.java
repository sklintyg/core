package se.inera.intyg.cts.infrastructure.integration.tellustalk.dto;

import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 *
 * @param to Phone number formated "sms:+46704000000"
 * @param text Text message to deliver
 * @param sms_originator_text Sender of SMS - Max 11 characters displayed as originator in cellphone
 */
public record SMSRequestDTO(String to, String text, String sms_originator_text) {

}
