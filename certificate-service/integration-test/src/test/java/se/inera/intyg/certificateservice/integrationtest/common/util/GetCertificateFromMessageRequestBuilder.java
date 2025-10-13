package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;

public class GetCertificateFromMessageRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static GetCertificateFromMessageRequestBuilder create() {
    return new GetCertificateFromMessageRequestBuilder();
  }

  private GetCertificateFromMessageRequestBuilder() {

  }

  public GetCertificateFromMessageRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public GetCertificateFromMessageRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetCertificateFromMessageRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public GetCertificateFromMessageRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public GetCertificateFromMessageRequest build() {
    return GetCertificateFromMessageRequest.builder()
        .user(user)
        .careProvider(careProvider)
        .unit(unit)
        .careUnit(careUnit)
        .build();
  }
}
