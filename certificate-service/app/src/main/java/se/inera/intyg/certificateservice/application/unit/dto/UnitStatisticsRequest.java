package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest.UnitStatisticsRequestBuilder;

@JsonDeserialize(builder = UnitStatisticsRequestBuilder.class)
@Value
@Builder
public class UnitStatisticsRequest {

  UserDTO user;
  List<String> issuedByUnitIds;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UnitStatisticsRequestBuilder {

  }
}
