package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.NursePrescriptionRight;
import se.riv.infrastructure.directory.authorizationmanagement.v2.NursePrescriptionRightType;

class NursePrescriptionRightTypeConverterTest {

  private static final NursePrescriptionRightTypeConverter converter = new NursePrescriptionRightTypeConverter();

  @Test
  void shouldConvertNull() {
    final var response = converter.convert(null);

    assertEquals(NursePrescriptionRight.builder().build(), response);
  }

  @Test
  void shouldConvertRight() {
    final var type = new NursePrescriptionRightType();
    type.setPrescriptionRight(true);

    final var response = converter.convert(type);

    assertEquals(type.isPrescriptionRight(), response.isPrescriptionRight());
  }

  @Test
  void shouldConvertLicence() {
    final var type = new NursePrescriptionRightType();
    type.setHealthCareProfessionalLicence(type.getHealthCareProfessionalLicence());

    final var response = converter.convert(type);

    assertEquals(type.getHealthCareProfessionalLicence(),
        response.getHealthCareProfessionalLicence());
  }
}