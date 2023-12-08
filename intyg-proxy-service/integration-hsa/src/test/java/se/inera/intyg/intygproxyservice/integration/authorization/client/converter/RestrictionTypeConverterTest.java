package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Restriction;
import se.riv.infrastructure.directory.authorizationmanagement.v2.RestrictionType;

@ExtendWith(MockitoExtension.class)
class RestrictionTypeConverterTest {

  @InjectMocks
  private RestrictionTypeConverter restrictionTypeConverter;

  @Test
  void shouldConvertCode() {
    final var type = new RestrictionType();
    type.setHealthCareProfessionalLicenceCode("CODE");

    final var response = restrictionTypeConverter.convert(type);

    assertEquals(
        type.getHealthCareProfessionalLicenceCode(),
        response.getHealthCareProfessionalLicenceCode()
    );
  }

  @Test
  void shouldConvertRestrictionCode() {
    final var type = new RestrictionType();
    type.setRestrictionCode("R_CODE");

    final var response = restrictionTypeConverter.convert(type);

    assertEquals(
        type.getRestrictionCode(),
        response.getRestrictionCode()
    );
  }

  @Test
  void shouldConvertRestrictionName() {
    final var type = new RestrictionType();
    type.setRestrictionName("R_NAME");

    final var response = restrictionTypeConverter.convert(type);

    assertEquals(
        type.getRestrictionName(),
        response.getRestrictionName()
    );
  }

  @Test
  void shouldReturnEmptyIfNull() {
    final var response = restrictionTypeConverter.convert(null);

    assertEquals(Restriction.builder().build(), response);
  }

}