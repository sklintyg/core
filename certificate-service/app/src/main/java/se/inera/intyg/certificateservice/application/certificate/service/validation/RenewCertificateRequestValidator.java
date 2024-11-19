package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;

@Component
public class RenewCertificateRequestValidator {

  public void validate(RenewCertificateRequest renewCertificateRequest, String certificateId) {
    validateUser(renewCertificateRequest.getUser());
    validateUnitExtended(renewCertificateRequest.getUnit(), "Unit");
    validateUnit(renewCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(renewCertificateRequest.getCareProvider(), "CareProvider");
    validatePatient(renewCertificateRequest.getPatient());
    validateCertificateId(certificateId);
  }
}
