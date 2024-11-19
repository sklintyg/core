package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueUnitContactInformationTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueUnitContactInformation.builder()
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfUnitContactInformationIsDefined() {
      assertFalse(
          ElementValueUnitContactInformation.builder()
              .address("Address")
              .city("City")
              .zipCode("ZipCode")
              .phoneNumber("PhoneNumber")
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfOneValueIsEmpty() {
      assertTrue(
          ElementValueUnitContactInformation.builder()
              .address("Address")
              .city("City")
              .zipCode("ZipCode")
              .build()
              .isEmpty()
      );
    }
  }

}