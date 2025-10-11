package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignRequest;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class CertificateReadyForSignRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static CertificateReadyForSignRequestBuilder create() {
    return new CertificateReadyForSignRequestBuilder();
  }

  private CertificateReadyForSignRequestBuilder() {

  }

  public CertificateReadyForSignRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public CertificateReadyForSignRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public CertificateReadyForSignRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public CertificateReadyForSignRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public CertificateReadyForSignRequest build() {
    return CertificateReadyForSignRequest.builder()
        .user(user)
        .careProvider(careProvider)
        .unit(unit)
        .careUnit(careUnit)
        .build();
  }
}
