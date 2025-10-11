package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;

public class HandleMessageRequestBuilder {

  private UnitDTO unit = ALFA_ALLERGIMOTTAGNINGEN_DTO;
  private UnitDTO careUnit = ALFA_MEDICINCENTRUM_DTO;
  private UserDTO user = AJLA_DOCTOR_DTO;
  private Boolean handled = true;

  public static HandleMessageRequestBuilder create() {
    return new HandleMessageRequestBuilder();
  }

  private HandleMessageRequestBuilder() {

  }

  public HandleMessageRequestBuilder unit(UnitDTO unit) {
    this.unit = unit;
    return this;
  }

  public HandleMessageRequestBuilder careUnit(UnitDTO careUnit) {
    this.careUnit = careUnit;
    return this;
  }

  public HandleMessageRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public HandleMessageRequestBuilder handled(Boolean handled) {
    this.handled = handled;
    return this;
  }

  public HandleMessageRequest build() {
    return HandleMessageRequest.builder()
        .user(user)
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(unit)
        .careUnit(careUnit)
        .handled(handled)
        .build();
  }
}
