package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateVersion;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateWithoutSignatureRequest;

@Component
public class SignCertificateWithoutSignatureRequestValidator {

  public void validate(SignCertificateWithoutSignatureRequest request, String certificateId,
      Long version) {
    validateUser(request.getUser());
    validateUnitExtended(request.getUnit(), "Unit");
    validateUnit(request.getCareUnit(), "CareUnit");
    validateUnit(request.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
    validateVersion(version);
  }
}
