package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateVersion;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;

@Component
public class DeleteCertificateRequestValidator {

  public void validate(DeleteCertificateRequest deleteCertificateRequest, String certificateId,
      Long version) {
    validateUser(deleteCertificateRequest.getUser());
    validateUnitExtended(deleteCertificateRequest.getUnit(), "Unit");
    validateUnit(deleteCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(deleteCertificateRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
    validateVersion(version);
  }
}
