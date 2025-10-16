package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;

public class CreateMessageRequestBuilder {

  private String message = "";
  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;
  private QuestionTypeDTO questionType = QuestionTypeDTO.CONTACT;

  public static CreateMessageRequestBuilder create() {
    return new CreateMessageRequestBuilder();
  }

  private CreateMessageRequestBuilder() {

  }

  public CreateMessageRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }


  public CreateMessageRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public CreateMessageRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public CreateMessageRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public CreateMessageRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public CreateMessageRequestBuilder content(String message) {
    this.message = message;
    return this;
  }

  public CreateMessageRequestBuilder content(QuestionTypeDTO questionType) {
    this.questionType = questionType;
    return this;
  }

  public CreateMessageRequest build() {
    return CreateMessageRequest.builder()
        .user(user)
        .unit(unit)
        .careUnit(careUnit)
        .careProvider(careProvider)
        .patient(patient)
        .message(message)
        .questionType(questionType)
        .build();
  }
}
