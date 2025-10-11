package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerRequest;

public class SendAnswerRequestBuilder {

  private String content = "answer content";
  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;


  public static SendAnswerRequestBuilder create() {
    return new SendAnswerRequestBuilder();
  }

  private SendAnswerRequestBuilder() {

  }

  public SendAnswerRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public SendAnswerRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public SendAnswerRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public SendAnswerRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public SendAnswerRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public SendAnswerRequestBuilder content(String content) {
    this.content = content;
    return this;
  }

  public SendAnswerRequest build() {
    return SendAnswerRequest.builder()
        .user(user)
        .unit(unit)
        .careUnit(careUnit)
        .careProvider(careProvider)
        .patient(patient)
        .content(content)
        .build();
  }
}
