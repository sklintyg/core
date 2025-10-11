package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;

public class SendMessageRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private PatientDTO patient = ATHENA_REACT_ANDERSSON_DTO;

  public static SendMessageRequestBuilder create() {
    return new SendMessageRequestBuilder();
  }

  private SendMessageRequestBuilder() {

  }

  public SendMessageRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public SendMessageRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public SendMessageRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public SendMessageRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public SendMessageRequestBuilder patient(PatientDTO patient) {
    this.patient = patient;
    return this;
  }

  public SendMessageRequest build() {
    return SendMessageRequest.builder()
        .user(user)
        .unit(unit)
        .careUnit(careUnit)
        .careProvider(careProvider)
        .patient(patient)
        .build();
  }
}
