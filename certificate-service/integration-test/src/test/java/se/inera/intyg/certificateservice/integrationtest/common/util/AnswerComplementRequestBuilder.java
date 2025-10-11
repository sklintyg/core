package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class AnswerComplementRequestBuilder {

  private String message = "message";
  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;


  public static AnswerComplementRequestBuilder create() {
    return new AnswerComplementRequestBuilder();
  }

  private AnswerComplementRequestBuilder() {

  }

  public AnswerComplementRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }


  public AnswerComplementRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public AnswerComplementRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public AnswerComplementRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public AnswerComplementRequestBuilder message(String message) {
    this.message = message;
    return this;
  }


  public AnswerComplementRequest build() {
    return AnswerComplementRequest.builder()
        .user(user)
        .unit(unit)
        .careUnit(careUnit)
        .careProvider(careProvider)
        .message(message)
        .build();
  }
}
