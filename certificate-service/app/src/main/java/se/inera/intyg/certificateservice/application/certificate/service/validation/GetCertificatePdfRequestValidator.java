package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateAdditonalInfoText;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;

@Component
public class GetCertificatePdfRequestValidator {

  public void validate(GetCertificatePdfRequest getCertificatePdfRequest, String certificateId) {
    validateUser(getCertificatePdfRequest.getUser());
    validateUnitExtended(getCertificatePdfRequest.getUnit(), "Unit");
    validateUnit(getCertificatePdfRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificatePdfRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
    validatePatient(getCertificatePdfRequest.getPatient());
    validateAdditonalInfoText(getCertificatePdfRequest.getAdditionalInfoText());
  }
}
