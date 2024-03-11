package se.inera.intyg.certificateservice.application.certificatetypeinfo.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;

@Component
public class CertificateTypeInfoValidator {

  public void validate(GetCertificateTypeInfoRequest getCertificateTypeInfoRequest) {
    validateUser(getCertificateTypeInfoRequest.getUser());
    validateUnitExtended(getCertificateTypeInfoRequest.getUnit(), "Unit");
    validateUnit(getCertificateTypeInfoRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificateTypeInfoRequest.getCareProvider(), "CareProvider");
    validatePatient(getCertificateTypeInfoRequest.getPatient());
  }
}
