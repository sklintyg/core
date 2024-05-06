package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;

@Component
public class ReplaceCertificateRequestValidator {

  public void validate(ReplaceCertificateRequest replaceCertificateRequest, String certificateId) {
    validateUser(replaceCertificateRequest.getUser());
    validateUnitExtended(replaceCertificateRequest.getUnit(), "Unit");
    validateUnit(replaceCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(replaceCertificateRequest.getCareProvider(), "CareProvider");
    validatePatient(replaceCertificateRequest.getPatient());
    validateCertificateId(certificateId);
  }
}
