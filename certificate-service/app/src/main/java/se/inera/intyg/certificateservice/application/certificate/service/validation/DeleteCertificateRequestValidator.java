package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUser;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateVersion;

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
    validatePatient(deleteCertificateRequest.getPatient());
    validateCertificateId(certificateId);
    validateVersion(version);
  }
}
