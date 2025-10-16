package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;

@Component
public class UpdateWithCertificateCandidateRequestValidator {

  public void validate(UpdateWithCertificateCandidateRequest request,
      String certificateId, String candidateCertificateId) {
    validateUser(request.getUser());
    validateUnitExtended(request.getUnit(), "Unit");
    validateUnit(request.getCareUnit(), "CareUnit");
    validateUnit(request.getCareProvider(), "CareProvider");
    validatePatient(request.getPatient());
    validateCertificateId(certificateId);
    if (candidateCertificateId == null || candidateCertificateId.isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: candidateCertificateId");
    }
  }
}