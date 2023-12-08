package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Restriction;
import se.riv.infrastructure.directory.authorizationmanagement.v2.RestrictionType;

@Component
public class RestrictionTypeConverter {

  public Restriction convert(RestrictionType type) {
    if (type == null) {
      return Restriction.builder().build();
    }

    return Restriction.builder()
        .healthCareProfessionalLicenceCode(type.getHealthCareProfessionalLicenceCode())
        .restrictionCode(type.getRestrictionCode())
        .restrictionName(type.getRestrictionName())
        .build();
  }
}
