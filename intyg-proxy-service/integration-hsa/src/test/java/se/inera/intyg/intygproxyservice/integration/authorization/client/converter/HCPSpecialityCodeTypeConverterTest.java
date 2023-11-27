package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HCPSpecialityCodesType;

class HCPSpecialityCodeTypeConverterTest {

  private static final HCPSpecialityCodeTypeConverter converter = new HCPSpecialityCodeTypeConverter();

  @Test
  void shouldConvertCode() {
    final var type = new HCPSpecialityCodesType();
    type.setSpecialityCode("CODE");

    final var response = converter.convert(type);

    assertEquals(type.getSpecialityCode(), response.getSpecialityCode());
  }

  @Test
  void shouldConvertName() {
    final var type = new HCPSpecialityCodesType();
    type.setSpecialityName("NAME");

    final var response = converter.convert(type);

    assertEquals(type.getSpecialityName(), response.getSpecialityName());
  }

  @Test
  void shouldConvertLicenseCode() {
    final var type = new HCPSpecialityCodesType();
    type.setHealthCareProfessionalLicenceCode("LICENSE CODE");

    final var response = converter.convert(type);

    assertEquals(type.getHealthCareProfessionalLicenceCode(),
        response.getHealthCareProfessionalLicenceCode());
  }

}