package se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.limitedfunctionality.dto.LimitedFunctionalityConfiguration.LimitedFunctionalityConfigurationBuilder;

@JsonDeserialize(builder = LimitedFunctionalityConfigurationBuilder.class)
@Builder
@Value
public class LimitedFunctionalityConfiguration {

  @JsonProperty("certificateType")
  String certificateType;
  @JsonProperty("version")
  List<String> version;
  @JsonProperty("configuration")
  LimitedFunctionalityActionsConfiguration configuration;

  @JsonPOJOBuilder(withPrefix = "")
  public static class LimitedFunctionalityConfigurationBuilder {

  }
}