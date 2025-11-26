package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HCPSpecialityCodes;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

@Component
public class SpecialitiesConverter {

  public HCPSpecialityCodes convert(Speciality speciality) {
    return HCPSpecialityCodes.builder()
        .healthCareProfessionalLicenceCode(speciality.getHealthCareProfessionalLicenceCode())
        .specialityName(speciality.getSpecialityName())
        .specialityCode(speciality.getSpecialityCode())
        .build();
  }
}
