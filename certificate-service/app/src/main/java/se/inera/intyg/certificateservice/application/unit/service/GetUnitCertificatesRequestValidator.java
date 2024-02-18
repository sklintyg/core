package se.inera.intyg.certificateservice.application.unit.service;

import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;

@Component
public class GetUnitCertificatesRequestValidator {

  public void validate(GetUnitCertificatesRequest getUnitCertificatesRequest) {
    validateUser(getUnitCertificatesRequest.getUser());
    validateUnitExtended(getUnitCertificatesRequest.getUnit(), "Unit");
    validateUnit(getUnitCertificatesRequest.getCareUnit(), "CareUnit");
    validateUnit(getUnitCertificatesRequest.getCareProvider(), "CareProvider");
    if (getUnitCertificatesRequest.getPatient() != null) {
      validatePatient(getUnitCertificatesRequest.getPatient());
    }
  }
}
