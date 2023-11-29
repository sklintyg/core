package se.inera.intyg.intygproxyservice.integration.api.authorization.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NursePrescriptionRight {

  String healthCareProfessionalLicence;
  boolean prescriptionRight;
}
