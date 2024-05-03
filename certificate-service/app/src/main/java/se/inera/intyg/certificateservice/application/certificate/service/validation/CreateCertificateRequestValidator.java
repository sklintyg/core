package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateModelId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

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
    validateCertificateModelId(createCertificateRequest.getCertificateModelId());
  }
}
