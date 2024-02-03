package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO;

public class GetCertificateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;

  public static GetCertificateRequestBuilder create() {
    return new GetCertificateRequestBuilder();
  }

  private GetCertificateRequestBuilder() {

  }

  public GetCertificateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public GetCertificateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetCertificateRequest build() {
    return GetCertificateRequest.builder()
        .user(TestDataCommonUserDTO.AJLA_DOCTOR_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .build();
  }
}
