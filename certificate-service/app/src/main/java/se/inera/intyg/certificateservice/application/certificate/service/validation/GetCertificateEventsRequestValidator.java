package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;

@Component
public class GetCertificateEventsRequestValidator {

  public void validate(GetCertificateEventsRequest getCertificateEventsRequest,
      String certificateId) {
    validateUser(getCertificateEventsRequest.getUser());
    validateUnitExtended(getCertificateEventsRequest.getUnit(), "Unit");
    validateUnit(getCertificateEventsRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificateEventsRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
  }
}
