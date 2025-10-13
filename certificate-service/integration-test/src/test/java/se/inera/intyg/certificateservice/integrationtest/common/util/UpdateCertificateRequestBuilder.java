package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class UpdateCertificateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;

  private CertificateDTO certificate;

  public static UpdateCertificateRequestBuilder create() {
    return new UpdateCertificateRequestBuilder();
  }

  private UpdateCertificateRequestBuilder() {

  }

  public UpdateCertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public UpdateCertificateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public UpdateCertificateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public UpdateCertificateRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public UpdateCertificateRequestBuilder certificate(CertificateDTO certificate) {
    this.certificate = certificate;
    return this;
  }

  public UpdateCertificateRequest build() {
    return UpdateCertificateRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .patient(patient)
        .certificate(certificate)
        .build();
  }
}
