package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataQuestionDTO.QUESTION_DTO_BUILDER;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageRequest;

public class SaveMessageRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private QuestionDTO question = QUESTION_DTO_BUILDER;

  public static SaveMessageRequestBuilder create() {
    return new SaveMessageRequestBuilder();
  }

  private SaveMessageRequestBuilder() {

  }

  public SaveMessageRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public SaveMessageRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public SaveMessageRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public SaveMessageRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public SaveMessageRequestBuilder question(QuestionDTO question) {
    this.question = question;
    return this;
  }

  public SaveMessageRequest build() {
    return SaveMessageRequest.builder()
        .user(user)
        .unit(unit)
        .careUnit(careUnit)
        .careProvider(careProvider)
        .question(question)
        .build();
  }
}
