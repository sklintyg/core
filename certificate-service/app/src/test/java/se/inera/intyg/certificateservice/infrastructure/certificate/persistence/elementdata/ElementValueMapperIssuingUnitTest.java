package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;

class ElementValueMapperIssuingUnitTest {

  private static final String ADDRESS = "address";
  private static final String ZIP_CODE = "zipCode";
  private static final String CITY = "city";
  private static final String PHONE_NUMBER = "phoneNumber";
  private ElementValueMapperIssuingUnit elementValueMapperIssuingUnit;

  @BeforeEach
  void setUp() {
    elementValueMapperIssuingUnit = new ElementValueMapperIssuingUnit();
  }

  @Test
  void shallSupportClassMappedElementValueIssuingUnit() {
    assertTrue(elementValueMapperIssuingUnit.supports(MappedElementValueIssuingUnit.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperIssuingUnit.supports(String.class));
  }

  @Test
  void shallSupportClassElementValueUnitContactInformation() {
    assertTrue(elementValueMapperIssuingUnit.supports(ElementValueUnitContactInformation.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueUnitContactInformation.builder()
        .address(ADDRESS)
        .zipCode(ZIP_CODE)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER)
        .build();

    final var mappedElementValueIssuingUnit = MappedElementValueIssuingUnit.builder()
        .address(ADDRESS)
        .zipCode(ZIP_CODE)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER)
        .build();

    final var actualValue = elementValueMapperIssuingUnit.toDomain(
        mappedElementValueIssuingUnit
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var expectedValue = MappedElementValueIssuingUnit.builder()
        .address(ADDRESS)
        .zipCode(ZIP_CODE)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER)
        .build();

    final var elementValueUnitContactInformation = ElementValueUnitContactInformation.builder()
        .address(ADDRESS)
        .zipCode(ZIP_CODE)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER)
        .build();

    final var actualValue = elementValueMapperIssuingUnit.toMapped(
        elementValueUnitContactInformation
    );

    assertEquals(expectedValue, actualValue);
  }
}
