package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

class HealthCareUnitMembersConverterTest {

  private HealthCareUnitMembersConverter healthCareUnitMembersConverter;

  private static final String VALUE = "value";
  private static final String NOT_FOUND = "-finns-ej";

  @BeforeEach
  void setUp() {
    healthCareUnitMembersConverter = new HealthCareUnitMembersConverter();
  }

  @Test
  void shouldFilterOutHsaIdsForSubUnitsThatEndWithNotFound() {
    final var parsedCareUnit = ParsedCareUnit.builder()
        .subUnits(
            List.of(
                ParsedSubUnit.builder()
                    .id(NOT_FOUND)
                    .build(),
                ParsedSubUnit.builder()
                    .id(NOT_FOUND)
                    .build(),
                ParsedSubUnit.builder()
                    .id(VALUE)
                    .build()
            )
        )
        .build();
    final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
    assertEquals(1, result.getHealthCareUnitMember().size());
  }

  @Nested
  class ConvertPrescriptionCode {

    @Test
    void shouldConvertPrescriptionCode() {
      final var parsedCareUnit = ParsedCareUnit.builder().prescriptionCode(VALUE).build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertTrue(result.getHealthCareUnitPrescriptionCode()
          .contains(parsedCareUnit.getPrescriptionCode()));
    }

    @Test
    void shouldNotConvertPrescriptionCode() {
      final var parsedCareUnit = ParsedCareUnit.builder().build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertTrue(result.getHealthCareUnitPrescriptionCode().isEmpty());
    }
  }

  @Nested
  class ConvertId {

    @Test
    void shouldConvertId() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertEquals(VALUE, result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberHsaId());
    }
  }

  @Nested
  class ConvertMemberName {

    @Test
    void shouldConvertMemberName() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .name(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertEquals(VALUE, result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberName());
    }

    @Test
    void shouldNotConvertMemberName() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertNull(result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberName());
    }
  }

  @Nested
  class ConvertMemberStartDate {

    @Test
    void shouldConvertMemberStartDate() {
      final var expectedResult = LocalDateTime.now();
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .start(expectedResult)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertEquals(expectedResult,
          result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberStartDate());
    }

    @Test
    void shouldNotConvertMemberStartDate() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertNull(result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberStartDate());
    }
  }

  @Nested
  class ConvertMemberEndDate {

    @Test
    void shouldConvertMemberEndDate() {
      final var expectedResult = LocalDateTime.now();
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .end(expectedResult)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertEquals(expectedResult,
          result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberEndDate());
    }

    @Test
    void shouldNotConvertMemberEndDate() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertNull(result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberEndDate());
    }
  }


  @Nested
  class ConvertMemberPostalAddress {

    @Test
    void shouldConvertMemberPostalAddress() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .postalAddress(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertEquals(VALUE,
          result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberpostalAddress().get(0));
    }

    @Test
    void shouldNotConvertMemberPostalAddress() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertTrue(
          result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberpostalAddress().isEmpty());
    }
  }

  @Nested
  class ConvertMemberPostalCode {

    @Test
    void shouldConvertMemberPostalCode() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .postalCode(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertEquals(VALUE,
          result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberpostalCode());
    }

    @Test
    void shouldNotConvertMemberPostalCode() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertNull(result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberpostalCode());
    }
  }

  @Nested
  class ConvertMemberPrescriptionCode {

    @Test
    void shouldConvertMemberPrescriptionCode() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .prescriptionCode(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertEquals(VALUE,
          result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberPrescriptionCode().get(0));
    }

    @Test
    void shouldNotConvertMemberPrescriptionCode() {
      final var parsedCareUnit = ParsedCareUnit.builder()
          .subUnits(
              List.of(
                  ParsedSubUnit.builder()
                      .id(VALUE)
                      .build()
              )
          )
          .build();
      final var result = healthCareUnitMembersConverter.convert(parsedCareUnit);
      assertTrue(result.getHealthCareUnitMember().get(0).getHealthCareUnitMemberPrescriptionCode()
          .isEmpty());
    }
  }
}
