package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateCertificateId;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;

@Component
public class GetCertificateXmlRequestValidator {

  public void validate(GetCertificateXmlRequest getCertificateXmlRequest, String certificateId) {
    validateUser(getCertificateXmlRequest.getUser());
    validateUnitExtended(getCertificateXmlRequest.getUnit(), "Unit");
    validateUnit(getCertificateXmlRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificateXmlRequest.getCareProvider(), "CareProvider");
    validateCertificateId(certificateId);
  }
}
