package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.MessagesQueryCriteriaDTO;

public class GetUnitMessagesRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UnitDTO careProvider = ALFA_REGIONEN_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private MessagesQueryCriteriaDTO messagesQueryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
      .build();

  public static GetUnitMessagesRequestBuilder create() {
    return new GetUnitMessagesRequestBuilder();
  }

  private GetUnitMessagesRequestBuilder() {

  }

  public GetUnitMessagesRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }


  public GetUnitMessagesRequestBuilder careProvider(UnitDTO careProvider) {
    this.careProvider = careProvider;
    return this;
  }

  public GetUnitMessagesRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public GetUnitMessagesRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public GetUnitMessagesRequestBuilder messagesQueryCriteria(
      MessagesQueryCriteriaDTO messagesQueryCriteria) {
    this.messagesQueryCriteriaDTO = messagesQueryCriteria;
    return this;
  }

  public GetUnitMessagesRequest build() {
    return GetUnitMessagesRequest.builder()
        .user(user)
        .unit(unit)
        .careUnit(careUnit)
        .careProvider(careProvider)
        .messagesQueryCriteria(messagesQueryCriteriaDTO)
        .build();
  }
}
