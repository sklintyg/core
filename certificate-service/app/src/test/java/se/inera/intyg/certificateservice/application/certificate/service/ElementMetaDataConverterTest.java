package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIssuingUnit;

class ElementMetaDataConverterTest {

  private static final String ADDRESS = "address";
  private ElementMetaDataConverter converter;

  private static final String ZIP_CODE = "zipCode";
  private static final String CITY = "city";
  private static final String PHONE_NUMBER = "phoneNumber";
  private static final UnitDTO UNIT_DTO = UnitDTO.builder()
      .address(ADDRESS)
      .zipCode(ZIP_CODE)
      .city(CITY)
      .phoneNumber(PHONE_NUMBER)
      .build();

  @BeforeEach
  void setUp() {
    converter = new ElementMetaDataConverter();
  }

  @Test
  void shallIncludeIssuingUnitAddress() {
    final var expectedAddressElement = (ElementValueIssuingUnit) ElementData.builder()
        .value(ElementValueIssuingUnit.builder()
            .address(ADDRESS)
            .build())
        .build().value();

    final var actualElements = (ElementValueIssuingUnit) converter.convert(UNIT_DTO).value();

    assertEquals(actualElements.address(), expectedAddressElement.address());
  }

  @Test
  void shallIncludeIssuingUnitZipCode() {
    final var expectedAddressElement = (ElementValueIssuingUnit) ElementData.builder()
        .value(ElementValueIssuingUnit.builder()
            .zipCode(ZIP_CODE)
            .build())
        .build().value();

    final var actualElements = (ElementValueIssuingUnit) converter.convert(UNIT_DTO).value();

    assertEquals(actualElements.zipCode(), expectedAddressElement.zipCode());
  }

  @Test
  void shallIncludeIssuingUnitCity() {
    final var expectedAddressElement = (ElementValueIssuingUnit) ElementData.builder()
        .value(ElementValueIssuingUnit.builder()
            .city(CITY)
            .build())
        .build().value();

    final var actualElements = (ElementValueIssuingUnit) converter.convert(UNIT_DTO).value();

    assertEquals(actualElements.city(), expectedAddressElement.city());
  }

  @Test
  void shallIncludeIssuingUnitPhoneNumber() {
    final var expectedAddressElement = (ElementValueIssuingUnit) ElementData.builder()
        .value(ElementValueIssuingUnit.builder()
            .phoneNumber(PHONE_NUMBER)
            .build())
        .build().value();

    final var actualElements = (ElementValueIssuingUnit) converter.convert(UNIT_DTO).value();

    assertEquals(actualElements.phoneNumber(), expectedAddressElement.phoneNumber());
  }
}