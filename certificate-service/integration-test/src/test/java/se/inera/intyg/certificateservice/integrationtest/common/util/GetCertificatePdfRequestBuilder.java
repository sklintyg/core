package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class GetCertificatePdfRequestBuilder {

  private static final String ADDITIONAL_INFO = "Intyget är utskrivet från Webcert.";
  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;


  public static GetCertificatePdfRequestBuilder create() {
    return new GetCertificatePdfRequestBuilder();
  }

  private GetCertificatePdfRequestBuilder() {

  }

  public GetCertificatePdfRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public GetCertificatePdfRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public GetCertificatePdfRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetCertificatePdfRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public GetCertificatePdfRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public GetCertificatePdfRequest build() {
    return GetCertificatePdfRequest.builder()
        .user(user)
        .careProvider(careProvider)
        .unit(unit)
        .careUnit(careUnit)
        .additionalInfoText(ADDITIONAL_INFO)
        .patient(patient)
        .build();
  }
}
