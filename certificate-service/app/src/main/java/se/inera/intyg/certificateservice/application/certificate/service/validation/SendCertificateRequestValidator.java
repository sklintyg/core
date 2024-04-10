package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateRequest;

@Component
public class SendCertificateRequestValidator {

  public void validate(SendCertificateRequest sendCertificateRequest, String certificateId) {
    validateUser(sendCertificateRequest.getUser());
    validateUnitExtended(sendCertificateRequest.getUnit(), "Unit");
    validateUnit(sendCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(sendCertificateRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
  }
}
