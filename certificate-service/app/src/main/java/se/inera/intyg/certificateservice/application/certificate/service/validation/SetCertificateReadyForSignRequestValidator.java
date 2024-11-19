package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignRequest;

@Component
public class SetCertificateReadyForSignRequestValidator {

  public void validate(CertificateReadyForSignRequest request,
      String certificateId) {
    validateUser(request.getUser());
    validateUnitExtended(request.getUnit(), "Unit");
    validateUnit(request.getCareUnit(), "CareUnit");
    validateUnit(request.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
  }
}
