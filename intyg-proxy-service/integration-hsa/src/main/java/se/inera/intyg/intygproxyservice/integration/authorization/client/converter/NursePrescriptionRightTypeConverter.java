package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.NursePrescriptionRight;
import se.riv.infrastructure.directory.authorizationmanagement.v2.NursePrescriptionRightType;

@Component
public class NursePrescriptionRightTypeConverter {

  public NursePrescriptionRight convert(NursePrescriptionRightType type) {
    if (type == null) {
      return NursePrescriptionRight.builder().build();
    }

    return NursePrescriptionRight.builder()
        .prescriptionRight(type.isPrescriptionRight())
        .healthCareProfessionalLicence(type.getHealthCareProfessionalLicence())
        .build();
  }
}
