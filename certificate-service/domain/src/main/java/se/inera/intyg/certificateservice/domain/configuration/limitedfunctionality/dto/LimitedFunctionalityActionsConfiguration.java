package se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto.LimitedFunctionalityActionsConfiguration.LimitedFunctionalityActionsConfigurationBuilder;

@JsonInclude
@JsonDeserialize(builder = LimitedFunctionalityActionsConfigurationBuilder.class)
@Builder
@Value
public class LimitedFunctionalityActionsConfiguration {

  @JsonProperty("actions")
  @Builder.Default
  List<LimitedActionConfiguration> actions = Collections.emptyList();

  @JsonPOJOBuilder(withPrefix = "")
  public static class LimitedFunctionalityActionsConfigurationBuilder {

  }
}