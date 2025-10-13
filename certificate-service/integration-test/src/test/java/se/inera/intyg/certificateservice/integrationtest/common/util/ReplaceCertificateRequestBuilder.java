package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class ReplaceCertificateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;

  public static ReplaceCertificateRequestBuilder create() {
    return new ReplaceCertificateRequestBuilder();
  }

  private ReplaceCertificateRequestBuilder() {

  }

  public ReplaceCertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public ReplaceCertificateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public ReplaceCertificateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public ReplaceCertificateRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public ReplaceCertificateRequest build() {
    return ReplaceCertificateRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .patient(patient)
        .build();
  }
}
