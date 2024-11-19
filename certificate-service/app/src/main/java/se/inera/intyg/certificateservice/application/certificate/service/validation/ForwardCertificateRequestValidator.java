package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;

@Component
public class ForwardCertificateRequestValidator {

  public void validate(ForwardCertificateRequest forwardCertificateRequest, String certificateId) {
    validateUser(forwardCertificateRequest.getUser());
    validateUnitExtended(forwardCertificateRequest.getUnit(), "Unit");
    validateUnit(forwardCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(forwardCertificateRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
  }
}
