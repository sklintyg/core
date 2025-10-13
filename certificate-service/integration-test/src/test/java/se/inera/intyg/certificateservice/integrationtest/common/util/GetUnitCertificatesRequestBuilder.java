package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;

public class GetUnitCertificatesRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;
  private CertificatesQueryCriteriaDTO queryCriteria = CertificatesQueryCriteriaDTO.builder()
      .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
      .build();

  public static GetUnitCertificatesRequestBuilder create() {
    return new GetUnitCertificatesRequestBuilder();
  }

  private GetUnitCertificatesRequestBuilder() {

  }

  public GetUnitCertificatesRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public GetUnitCertificatesRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetUnitCertificatesRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public GetUnitCertificatesRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public GetUnitCertificatesRequestBuilder queryCriteria(
      CertificatesQueryCriteriaDTO queryCriteria) {
    this.queryCriteria = queryCriteria;
    return this;
  }

  public GetUnitCertificatesRequest build() {
    return GetUnitCertificatesRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .patient(patient)
        .certificatesQueryCriteria(queryCriteria)
        .build();
  }
}
