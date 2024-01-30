package se.inera.intyg.certificateservice.application.certificate.service;

import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;

@Component
public class CreateCertificateRequestValidator {

  public void validate(CreateCertificateRequest createCertificateRequest) {
    validateUser(createCertificateRequest.getUser());
    validateUnitExtended(createCertificateRequest.getUnit(), "Unit");
    validateUnit(createCertificateRequest.getCareUnit(), "CareUnit");
    validateUnit(createCertificateRequest.getCareProvider(), "CareProvider");
    validatePatient(createCertificateRequest.getPatient());
    validateCertificateId(createCertificateRequest.getCertificateModelId());
  }
}
