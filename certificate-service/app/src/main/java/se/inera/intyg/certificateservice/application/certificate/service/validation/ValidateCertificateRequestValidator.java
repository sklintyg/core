package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificate;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;

@Component
public class ValidateCertificateRequestValidator {

  public void validate(ValidateCertificateRequest validateCertificateRequest,
      String certificateId) {
    validateUser(validateCertificateRequest.getUser());
    validateUnitExtended(validateCertificateRequest.getUnit(), "Unit");
    validateUnit(validateCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(validateCertificateRequest.getCareProvider(), "CareProvider");
    validatePatient(validateCertificateRequest.getPatient());
    validateCertificateId(certificateId);
    validateCertificate(validateCertificateRequest.getCertificate());
  }
}
