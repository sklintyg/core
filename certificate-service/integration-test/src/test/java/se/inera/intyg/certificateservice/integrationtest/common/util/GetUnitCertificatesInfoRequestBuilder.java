package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;

public class GetUnitCertificatesInfoRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static GetUnitCertificatesInfoRequestBuilder create() {
    return new GetUnitCertificatesInfoRequestBuilder();
  }

  private GetUnitCertificatesInfoRequestBuilder() {

  }

  public GetUnitCertificatesInfoRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public GetUnitCertificatesInfoRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetUnitCertificatesInfoRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public GetUnitCertificatesInfoRequest build() {
    return GetUnitCertificatesInfoRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .build();
  }
}
