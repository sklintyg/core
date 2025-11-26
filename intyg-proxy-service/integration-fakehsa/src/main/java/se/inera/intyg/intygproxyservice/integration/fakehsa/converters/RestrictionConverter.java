package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Restriction;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Restrictions;

@Component
public class RestrictionConverter {

  public Restriction convert(Restrictions restriction) {
    return Restriction.builder()
        .healthCareProfessionalLicenceCode(restriction.getHealthCareProfessionalLicenceCode())
        .restrictionName(restriction.getRestrictionName())
        .restrictionCode(restriction.getRestrictionCode())
        .build();
  }
}
