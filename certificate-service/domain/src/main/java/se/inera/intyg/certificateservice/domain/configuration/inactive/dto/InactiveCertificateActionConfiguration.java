package se.inera.intyg.certificateservice.domain.configuration.inactive.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.InactiveCertificateActionConfiguration.InactiveCertificateActionConfigurationBuilder;

@JsonInclude
@JsonDeserialize(builder = InactiveCertificateActionConfigurationBuilder.class)
@Builder
@Value
public class InactiveCertificateActionConfiguration {

  @JsonProperty("type")
  String type;
  @JsonProperty("untilDateTime")
  LocalDateTime untilDateTime;

  @JsonPOJOBuilder(withPrefix = "")
  public static class InactiveCertificateActionConfigurationBuilder {

  }
}