package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;

import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class UpdateWithCertificateCandidateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = ajlaDoktorDtoBuilder()
      .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
      .build();
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;

  public static UpdateWithCertificateCandidateRequestBuilder create() {
    return new UpdateWithCertificateCandidateRequestBuilder();
  }

  private UpdateWithCertificateCandidateRequestBuilder() {

  }

  public UpdateWithCertificateCandidateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public UpdateWithCertificateCandidateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public UpdateWithCertificateCandidateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public UpdateWithCertificateCandidateRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public UpdateWithCertificateCandidateRequest build() {
    return UpdateWithCertificateCandidateRequest.builder()
        .careProvider(ALFA_REGIONEN_DTO)
        .careUnit(careUnit)
        .user(user)
        .unit(unit)
        .patient(patient)
        .build();
  }
}