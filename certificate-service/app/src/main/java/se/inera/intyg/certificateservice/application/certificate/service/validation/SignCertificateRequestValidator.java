package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateVersion;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;

@Component
public class SignCertificateRequestValidator {

  public void validate(SignCertificateRequest request, String certificateId, Long version) {
    validateUser(request.getUser());
    validateUnitExtended(request.getUnit(), "Unit");
    validateUnit(request.getCareUnit(), "CareUnit");
    validateUnit(request.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
    validateVersion(version);
    if (request.getSignatureXml() == null || request.getSignatureXml().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: signatureXml");
    }
  }
}
