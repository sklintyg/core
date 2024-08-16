package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsDTO.UnitStatisticsDTOBuilder;

@JsonDeserialize(builder = UnitStatisticsDTOBuilder.class)
@Value
@Builder
public class UnitStatisticsDTO {

  int draftCount;
  int unhandledMessageCount;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UnitStatisticsDTOBuilder {

  }
}
