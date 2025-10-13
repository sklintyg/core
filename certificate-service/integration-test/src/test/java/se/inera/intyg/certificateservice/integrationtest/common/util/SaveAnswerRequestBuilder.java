package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerRequest;

public class SaveAnswerRequestBuilder {

  private String content = "save answer content";
  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static SaveAnswerRequestBuilder create() {
    return new SaveAnswerRequestBuilder();
  }

  private SaveAnswerRequestBuilder() {

  }

  public SaveAnswerRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }
  
  public SaveAnswerRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public SaveAnswerRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public SaveAnswerRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public SaveAnswerRequestBuilder content(String content) {
    this.content = content;
    return this;
  }

  public SaveAnswerRequest build() {
    return SaveAnswerRequest.builder()
        .user(user)
        .unit(unit)
        .careUnit(careUnit)
        .careProvider(careProvider)
        .content(content)
        .build();
  }
}
