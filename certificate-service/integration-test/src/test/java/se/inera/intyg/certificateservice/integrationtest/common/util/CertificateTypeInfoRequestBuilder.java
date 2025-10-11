package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class CertificateTypeInfoRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;

  public static CertificateTypeInfoRequestBuilder create() {
    return new CertificateTypeInfoRequestBuilder();
  }

  private CertificateTypeInfoRequestBuilder() {

  }

  public CertificateTypeInfoRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public CertificateTypeInfoRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public CertificateTypeInfoRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public GetCertificateTypeInfoRequest build() {
    return GetCertificateTypeInfoRequest.builder()
        .user(user)
        .patient(patient)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .build();
  }
}
