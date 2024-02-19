package se.inera.intyg.certificateservice.application.unit.service;

import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;

@Component
public class GetUnitCertificatesInfoRequestValidator {

  public void validate(GetUnitCertificatesInfoRequest getUnitCertificatesInfoRequest) {
    validateUser(getUnitCertificatesInfoRequest.getUser());
    validateUnitExtended(getUnitCertificatesInfoRequest.getUnit(), "Unit");
    validateUnit(getUnitCertificatesInfoRequest.getCareUnit(), "CareUnit");
    validateUnit(getUnitCertificatesInfoRequest.getCareProvider(), "CareProvider");
  }
}
