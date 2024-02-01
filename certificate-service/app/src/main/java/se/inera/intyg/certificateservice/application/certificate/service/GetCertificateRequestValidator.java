package se.inera.intyg.certificateservice.application.certificate.service;

import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;

@Component
public class GetCertificateRequestValidator {

  public void validate(GetCertificateRequest getCertificateRequest, String certificateId) {
    validateUser(getCertificateRequest.getUser());
    validateUnitExtended(getCertificateRequest.getUnit(), "Unit");
    validateUnit(getCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificateRequest.getCareProvider(), "CareProvider");
    validatePatient(getCertificateRequest.getPatient());
    validateCertificateId(certificateId);
  }
}
