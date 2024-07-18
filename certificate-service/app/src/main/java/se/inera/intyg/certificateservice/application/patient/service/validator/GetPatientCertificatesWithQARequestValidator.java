package se.inera.intyg.certificateservice.application.patient.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePersonId;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;

@Component
public class GetPatientCertificatesWithQARequestValidator {

  public void validate(PatientCertificatesWithQARequest request) {
    validatePersonId(request.getPersonId());
    if ((request.getCareProviderId() == null || request.getCareProviderId().isBlank())
        && (request.getUnitIds() == null || request.getUnitIds().isEmpty())) {
      throw new IllegalArgumentException("Required parameter missing: unitIds or careProviderId");
    }
  }
}
