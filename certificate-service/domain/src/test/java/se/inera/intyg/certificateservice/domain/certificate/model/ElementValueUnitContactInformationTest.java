package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.contactInfoElementValueBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.alfaAllergimottagningenBuilder;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;

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

  @Nested
  class CopyBasedOnIssuedUnitTest {

    @Test
    void shouldReturnAddressIfIssuedUnitAddressNull() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.address(), actual.address());
    }

    @Test
    void shouldReturnAddressIfIssuedUnitAddressEmpty() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .address("")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.address(), actual.address());
    }

    @Test
    void shouldReturnIssuedUnitAddressIfIssuedUnitAddressExists() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .address("New Address")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals("New Address", actual.address());
    }

    @Test
    void shouldReturnCityIfIssuedUnitCityNull() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.city(), actual.city());
    }

    @Test
    void shouldReturnCityIfIssuedUnitCityEmpty() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .city("")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.city(), actual.city());
    }

    @Test
    void shouldReturnIssuedUnitCityIfIssuedUnitCityExists() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .city("New City")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals("New City", actual.city());
    }

    @Test
    void shouldReturnZipCodeIfIssuedUnitZipCodeNull() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.zipCode(), actual.zipCode());
    }

    @Test
    void shouldReturnZipCodeIfIssuedUnitZipCodeEmpty() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .zipCode("")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.zipCode(), actual.zipCode());
    }

    @Test
    void shouldReturnIssuedUnitZipCodeIfIssuedUnitZipCodeExists() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .address(
              UnitAddress.builder()
                  .zipCode("New ZipCode")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals("New ZipCode", actual.zipCode());
    }

    @Test
    void shouldReturnPhoneNumberIfIssuedUnitPhoneNumberNull() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .contactInfo(
              UnitContactInfo.builder()
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.phoneNumber(), actual.phoneNumber());
    }

    @Test
    void shouldReturnPhoneNumberIfIssuedUnitPhoneNumberEmpty() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .contactInfo(
              UnitContactInfo.builder()
                  .phoneNumber("")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals(original.phoneNumber(), actual.phoneNumber());
    }

    @Test
    void shouldReturnIssuedUnitPhoneNumberIfIssuedUnitPhoneNumberExists() {
      final var original = contactInfoElementValueBuilder().build();
      final var issuingUnit = alfaAllergimottagningenBuilder()
          .contactInfo(
              UnitContactInfo.builder()
                  .phoneNumber("New PhoneNumber")
                  .build()
          )
          .build();

      final var actual = original.copy(issuingUnit);
      assertEquals("New PhoneNumber", actual.phoneNumber());
    }
  }
}