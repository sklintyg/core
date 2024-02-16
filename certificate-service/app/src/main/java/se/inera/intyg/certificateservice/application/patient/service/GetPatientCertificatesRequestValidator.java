package se.inera.intyg.certificateservice.application.patient.service;

import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.ValidationUtil.validateUser;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;

@Component
public class GetPatientCertificatesRequestValidator {

  public void validate(GetPatientCertificatesRequest getPatientCertificatesRequest) {
    validateUser(getPatientCertificatesRequest.getUser());
    validateUnitExtended(getPatientCertificatesRequest.getUnit(), "Unit");
    validateUnit(getPatientCertificatesRequest.getCareUnit(), "CareUnit");
    validateUnit(getPatientCertificatesRequest.getCareProvider(), "CareProvider");
    validatePatient(getPatientCertificatesRequest.getPatient());
  }
}
