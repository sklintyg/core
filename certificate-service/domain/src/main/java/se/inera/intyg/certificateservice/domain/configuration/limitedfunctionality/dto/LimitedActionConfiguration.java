package se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto.LimitedActionConfiguration.LimitedActionConfigurationBuilder;

@JsonInclude
@JsonDeserialize(builder = LimitedActionConfigurationBuilder.class)
@Builder
@Value
public class LimitedActionConfiguration {

  @JsonProperty("type")
  String type;
  @JsonProperty("untilDateTime")
  LocalDateTime untilDateTime;

  @JsonPOJOBuilder(withPrefix = "")
  public static class LimitedActionConfigurationBuilder {

  }
}