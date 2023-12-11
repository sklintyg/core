package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HealthCareProfessionalLicence;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HealthCareProfessionalLicenceType;

@Component
public class HealthCareProfessionalLicenceTypeConverter {

  public HealthCareProfessionalLicence convert(HealthCareProfessionalLicenceType type) {
    if (type == null) {
      return HealthCareProfessionalLicence.builder().build();
    }

    return HealthCareProfessionalLicence
        .builder()
        .healthCareProfessionalLicenceCode(type.getHealthCareProfessionalLicenceCode())
        .healthCareProfessionalLicenceName(type.getHealthCareProfessionalLicenceName())
        .build();
  }
}
