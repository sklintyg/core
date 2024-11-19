package se.inera.intyg.certificateservice.application.message.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;

@Component
public class GetCertificateMessageRequestValidator {

  public void validate(GetCertificateMessageRequest getCertificateMessageRequest,
      String certificateId) {
    validateUser(getCertificateMessageRequest.getUser());
    validateUnitExtended(getCertificateMessageRequest.getUnit(), "Unit");
    validateUnit(getCertificateMessageRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificateMessageRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
    validatePatient(getCertificateMessageRequest.getPatient());
  }
}
