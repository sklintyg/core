package se.inera.intyg.cts.infrastructure.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MessageFormatterTest {

  private final MessageFormatter messageFormatter = new MessageFormatter();

  private static final String COMPLIANT_PHONE_NUMBER = "sms:+46701234567";

  @Nested
  class TestPhoneNumberFormatting {

    @Test
    void shouldHandleCompliantPhoneNumber() {
      final var formattedPhoneNumber = messageFormatter.formatPhoneNumber(COMPLIANT_PHONE_NUMBER);
      assertEquals(COMPLIANT_PHONE_NUMBER, formattedPhoneNumber);
    }

    @Test
    void shouldHandlePhoneNumberWithCountryCode() {
      final var formattedPhoneNumber = messageFormatter.formatPhoneNumber("+46701234567");
      assertEquals(COMPLIANT_PHONE_NUMBER, formattedPhoneNumber);
    }

    @Test
    void shouldHandleStandardPhoneNumberFormat() {
      final var formattedPhoneNumber = messageFormatter.formatPhoneNumber("070-1234567");
      assertEquals(COMPLIANT_PHONE_NUMBER, formattedPhoneNumber);
    }

    @Test
    void shouldHandleSomeNonStandardPhoneNumberFormat() {
      final var formattedPhoneNumber = messageFormatter.formatPhoneNumber("+70-123R4 5-67");
      assertEquals(COMPLIANT_PHONE_NUMBER, formattedPhoneNumber);
    }
  }

}