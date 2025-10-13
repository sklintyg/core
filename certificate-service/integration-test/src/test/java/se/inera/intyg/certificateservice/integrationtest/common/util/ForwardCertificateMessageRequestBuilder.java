package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class ForwardCertificateMessageRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static ForwardCertificateMessageRequestBuilder create() {
    return new ForwardCertificateMessageRequestBuilder();
  }

  private ForwardCertificateMessageRequestBuilder() {

  }

  public ForwardCertificateMessageRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public ForwardCertificateMessageRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public ForwardCertificateMessageRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public ForwardCertificateRequest build() {
    return ForwardCertificateRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .build();
  }
}
