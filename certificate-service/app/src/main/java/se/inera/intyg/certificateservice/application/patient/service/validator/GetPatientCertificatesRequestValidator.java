package se.inera.intyg.certificateservice.application.patient.service.validator;

import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validatePatient;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnit;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUnitExtended;
import static se.inera.intyg.certificateservice.application.common.validator.ValidationUtil.validateUser;

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
