package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIssuingUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Inactive;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CertificateMetaDataUnitConverterTest {

  private static final String EXPECTED_UNIT_NAME = "expectedUnitName";
  private static final String EXPECTED_ADDRESS = "expectedAddress";
  private static final String EXPECTED_CITY = "expectedCity";
  private static final String EXPECTED_ZIP_CODE = "expectedZipCode";
  private static final String EXPECTED_PHONE_NUMBER = "expectedPhoneNumber";
  private static final String EXPECTED_EMAIL = "expectedEmail";
  private CertificateMetaDataUnitConverter certificateMetaDataUnitConverter;
  private static final String ISSUING_UNIT = "ISSUING_UNIT";
  private static final String EXPECTED_ID = "expectedId";
  private CareUnit.CareUnitBuilder careUnitBuilder;

  @BeforeEach
  void setUp() {
    certificateMetaDataUnitConverter = new CertificateMetaDataUnitConverter();
    careUnitBuilder = CareUnit.builder()
        .name(
            new UnitName("name")
        )
        .address(
            UnitAddress.builder()
                .address("address")
                .city("city")
                .zipCode("zipCode")
                .build()
        )
        .hsaId(new HsaId("hsaId"))
        .contactInfo(
            UnitContactInfo.builder()
                .phoneNumber("phoneNumber")
                .email("email")
                .build()
        )
        .inactive(new Inactive(false)
        );
  }

  @Test
  void shallConvertUnitId() {
    final var issuingUnit = careUnitBuilder.hsaId(new HsaId(EXPECTED_ID)).build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertEquals(EXPECTED_ID, result.getUnitId());
  }

  @Test
  void shallConvertName() {
    final var issuingUnit = careUnitBuilder.name(new UnitName(EXPECTED_UNIT_NAME)).build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertEquals(EXPECTED_UNIT_NAME, result.getUnitName());
  }

  @Test
  void shallConvertAddressFromIssuingUnit() {
    final var issuingUnit = careUnitBuilder
        .address(
            UnitAddress.builder()
                .address(EXPECTED_ADDRESS)
                .city("city")
                .zipCode("zipCode")
                .build()
        )
        .build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertEquals(EXPECTED_ADDRESS, result.getAddress());
  }

  @Test
  void shallConvertAddressCityFromElementData() {
    final var issuingUnit = careUnitBuilder.build();
    final var elementData = List.of(
        getElementData(ISSUING_UNIT, EXPECTED_ADDRESS, null, null, null)
    );
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit, elementData);
    assertEquals(EXPECTED_ADDRESS, result.getAddress());
  }

  @Test
  void shallConvertCityFromIssuingUnit() {
    final var issuingUnit = careUnitBuilder
        .address(
            UnitAddress.builder()
                .address("address")
                .city(EXPECTED_CITY)
                .zipCode("zipCode")
                .build()
        )
        .build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertEquals(EXPECTED_CITY, result.getCity());
  }

  @Test
  void shallConvertCityFromElementData() {
    final var issuingUnit = careUnitBuilder.build();
    final var elementData = List.of(
        getElementData(ISSUING_UNIT, null, EXPECTED_CITY, null, null)
    );
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit, elementData);
    assertEquals(EXPECTED_CITY, result.getCity());
  }

  @Test
  void shallConvertZipcodeFromIssuingUnit() {
    final var issuingUnit = careUnitBuilder
        .address(
            UnitAddress.builder()
                .address("address")
                .city("city")
                .zipCode(EXPECTED_ZIP_CODE)
                .build()
        )
        .build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertEquals(EXPECTED_ZIP_CODE, result.getZipCode());
  }

  @Test
  void shallConvertZipcodeFromElementData() {
    final var issuingUnit = careUnitBuilder.build();
    final var elementData = List.of(
        getElementData(ISSUING_UNIT, null, null, EXPECTED_ZIP_CODE, null)
    );
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit, elementData);
    assertEquals(EXPECTED_ZIP_CODE, result.getZipCode());
  }

  @Test
  void shallConvertPhoneNumberFromIssuingUnit() {
    final var issuingUnit = careUnitBuilder
        .contactInfo(
            UnitContactInfo.builder()
                .phoneNumber(EXPECTED_PHONE_NUMBER)
                .build()
        )
        .build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertEquals(EXPECTED_PHONE_NUMBER, result.getPhoneNumber());
  }

  @Test
  void shallConvertPhoneNumberFromElementData() {
    final var issuingUnit = careUnitBuilder.build();
    final var elementData = List.of(
        getElementData(ISSUING_UNIT, null, null, null, EXPECTED_PHONE_NUMBER)
    );
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit, elementData);
    assertEquals(EXPECTED_PHONE_NUMBER, result.getPhoneNumber());
  }

  @Test
  void shallConvertEmailFromIssuingUnit() {
    final var issuingUnit = careUnitBuilder
        .contactInfo(
            UnitContactInfo.builder()
                .phoneNumber("phoneNumber")
                .email(EXPECTED_EMAIL)
                .build()
        )
        .build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertEquals(EXPECTED_EMAIL, result.getEmail());
  }

  @Test
  void shallConvertIsInactiveFromIssuingUnit() {
    final var issuingUnit = careUnitBuilder
        .inactive(new Inactive(true))
        .build();
    final var result = certificateMetaDataUnitConverter.convert(issuingUnit,
        Collections.emptyList());
    assertTrue(result.getIsInactive());
  }

  private static ElementData getElementData(String id, String address, String city, String zipcode,
      String phoneNumber) {
    return ElementData.builder()
        .id(new ElementId(id))
        .value(ElementValueIssuingUnit.builder()
            .address(address)
            .city(city)
            .zipCode(zipcode)
            .phoneNumber(phoneNumber)
            .build()
        )
        .build();
  }
}
