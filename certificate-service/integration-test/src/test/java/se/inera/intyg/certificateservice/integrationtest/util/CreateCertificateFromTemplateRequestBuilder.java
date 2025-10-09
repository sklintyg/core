package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class CreateCertificateFromTemplateRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static CreateCertificateFromTemplateRequestBuilder create() {
    return new CreateCertificateFromTemplateRequestBuilder();
  }

  private CreateCertificateFromTemplateRequestBuilder() {

  }

  public CreateCertificateFromTemplateRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public CreateCertificateFromTemplateRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public CreateCertificateFromTemplateRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public CreateDraftFromCertificateRequest build() {
    return CreateDraftFromCertificateRequest.builder()
        .careProvider(ALFA_REGIONEN_DTO)
        .careUnit(careUnit)
        .user(user)
        .unit(unit)
        .build();
  }
}