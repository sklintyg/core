package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse.UnitStatisticsResponseBuilder;

@JsonDeserialize(builder = UnitStatisticsResponseBuilder.class)
@Value
@Builder
public class UnitStatisticsResponse {

  Map<String, UnitStatisticsDTO> unitStatistics;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UnitStatisticsResponseBuilder {

  }
}
