package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;

@Component
public class GetCertificateRequestValidator {

  public void validate(GetCertificateRequest getCertificateRequest, String certificateId) {
    validateUser(getCertificateRequest.getUser());
    validateUnitExtended(getCertificateRequest.getUnit(), "Unit");
    validateUnit(getCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificateRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
  }
}
