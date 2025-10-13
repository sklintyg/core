package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class RenewCertificateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;

  public static RenewCertificateRequestBuilder create() {
    return new RenewCertificateRequestBuilder();
  }

  private RenewCertificateRequestBuilder() {

  }

  public RenewCertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public RenewCertificateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public RenewCertificateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public RenewCertificateRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public RenewCertificateRequest build() {
    return RenewCertificateRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .patient(patient)
        .build();
  }
}
