package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;

public class UnitStatisticsRequestBuilder {

  private List<String> issuedByUnitIds = List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId());
  private UserDTO user = AJLA_DOCTOR_DTO;

  public static UnitStatisticsRequestBuilder create() {
    return new UnitStatisticsRequestBuilder();
  }

  private UnitStatisticsRequestBuilder() {

  }

  public UnitStatisticsRequestBuilder user(UserDTO user) {
    this.user = user;
    return this;
  }

  public UnitStatisticsRequestBuilder availableUnitIds(List<String> availableUnitIds) {
    this.issuedByUnitIds = availableUnitIds;
    return this;
  }

  public UnitStatisticsRequest build() {
    return UnitStatisticsRequest.builder()
        .user(user)
        .issuedByUnitIds(issuedByUnitIds)
        .build();
  }
}
