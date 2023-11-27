package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HCPSpecialityCodes;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HCPSpecialityCodesType;

@Component
public class HCPSpecialityCodeTypeConverter {

  public HCPSpecialityCodes convert(HCPSpecialityCodesType type) {
    return HCPSpecialityCodes
        .builder()
        .specialityCode(type.getSpecialityCode())
        .specialityName(type.getSpecialityName())
        .healthCareProfessionalLicenceCode(type.getHealthCareProfessionalLicenceCode())
        .build();
  }
}
