package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HealthCareProfessionalLicence;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.HealthCareProfessionalLicenceType;

@Component
public class ProfessionalLicenceTypeConverter {

  public HealthCareProfessionalLicence convert(
      HealthCareProfessionalLicenceType healthCareProfessionalLicenceType) {
    return HealthCareProfessionalLicence.builder()
        .healthCareProfessionalLicenceName(
            healthCareProfessionalLicenceType.getHealthCareProfessionalLicenceName())
        .healthCareProfessionalLicenceCode(
            healthCareProfessionalLicenceType.getHealthCareProfessionalLicenceCode())
        .build();
  }
}
