package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateCertificate;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;

@Component
public class UpdateCertificateRequestValidator {

  public void validate(UpdateCertificateRequest updateCertificateRequest, String certificateId) {
    validateUser(updateCertificateRequest.getUser());
    validateUnitExtended(updateCertificateRequest.getUnit(), "Unit");
    validateUnit(updateCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(updateCertificateRequest.getCareProvider(), "CareProvider");
    validatePatient(updateCertificateRequest.getPatient());
    validateCertificateId(certificateId);
    validateCertificate(updateCertificateRequest.getCertificate());
  }
}
