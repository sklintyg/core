package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HealthCareProfessionalLicence;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HealthCareProfessionalLicenceType;

@ExtendWith(MockitoExtension.class)
class HealthCareProfessionalLicenceTypeConverterTest {

  @InjectMocks
  private HealthCareProfessionalLicenceTypeConverter converter;

  @Test
  void shouldConvertCode() {
    final var type = new HealthCareProfessionalLicenceType();
    type.setHealthCareProfessionalLicenceCode("CODE");

    final var response = converter.convert(type);

    assertEquals(type.getHealthCareProfessionalLicenceCode(),
        response.getHealthCareProfessionalLicenceCode());
  }

  @Test
  void shouldConvertName() {
    final var type = new HealthCareProfessionalLicenceType();
    type.setHealthCareProfessionalLicenceName("NAME");

    final var response = converter.convert(type);

    assertEquals(type.getHealthCareProfessionalLicenceName(),
        response.getHealthCareProfessionalLicenceName());
  }

  @Test
  void shouldReturnEmptyIfNull() {
    final var response = converter.convert(null);

    assertEquals(HealthCareProfessionalLicence.builder().build(), response);
  }

}