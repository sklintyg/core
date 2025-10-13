package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class GetCertificateEventsRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static GetCertificateEventsRequestBuilder create() {
    return new GetCertificateEventsRequestBuilder();
  }

  private GetCertificateEventsRequestBuilder() {

  }

  public GetCertificateEventsRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public GetCertificateEventsRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetCertificateEventsRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public GetCertificateEventsRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public GetCertificateEventsRequest build() {
    return GetCertificateEventsRequest.builder()
        .user(user)
        .careProvider(careProvider)
        .unit(unit)
        .careUnit(careUnit)
        .build();
  }
}
