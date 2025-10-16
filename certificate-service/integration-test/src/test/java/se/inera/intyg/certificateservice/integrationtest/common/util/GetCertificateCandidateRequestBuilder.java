package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;

import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class GetCertificateCandidateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = ajlaDoktorDtoBuilder()
      .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
      .build();

  public static GetCertificateCandidateRequestBuilder create() {
    return new GetCertificateCandidateRequestBuilder();
  }

  private GetCertificateCandidateRequestBuilder() {

  }

  public GetCertificateCandidateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public GetCertificateCandidateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetCertificateCandidateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public GetCertificateCandidateRequest build() {
    return GetCertificateCandidateRequest.builder()
        .careProvider(ALFA_REGIONEN_DTO)
        .careUnit(careUnit)
        .user(user)
        .unit(unit)
        .build();
  }
}