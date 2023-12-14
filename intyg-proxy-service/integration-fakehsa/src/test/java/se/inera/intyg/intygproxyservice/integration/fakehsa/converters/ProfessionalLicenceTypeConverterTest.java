package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.HealthCareProfessionalLicenceType;

@ExtendWith(MockitoExtension.class)
class ProfessionalLicenceTypeConverterTest {

  @InjectMocks
  private ProfessionalLicenceTypeConverter licenceTypeConverter;

  private static final String LICENCE_NAME = "licenceName";
  private static final String LICENCE_CODE = "licenceCode";
  private static final HealthCareProfessionalLicenceType HEALTH_CARE_PROFESSIONAL_LICENCE_TYPE
      = HealthCareProfessionalLicenceType.builder()
      .healthCareProfessionalLicenceName(LICENCE_NAME)
      .healthCareProfessionalLicenceCode(LICENCE_CODE)
      .build();

  @Test
  void shouldConvertName() {
    final var result = licenceTypeConverter.convert(HEALTH_CARE_PROFESSIONAL_LICENCE_TYPE);
    assertEquals(LICENCE_NAME, result.getHealthCareProfessionalLicenceName());
  }

  @Test
  void shouldConvertCode() {
    final var result = licenceTypeConverter.convert(HEALTH_CARE_PROFESSIONAL_LICENCE_TYPE);
    assertEquals(LICENCE_CODE, result.getHealthCareProfessionalLicenceCode());
  }
}
