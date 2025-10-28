package se.inera.intyg.certificateservice.domain.configuration.inactive.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.InactiveCertificateConfiguration.InactiveCertificateConfigurationBuilder;

@JsonInclude
@JsonDeserialize(builder = InactiveCertificateConfigurationBuilder.class)
@Builder
@Value
public class InactiveCertificateConfiguration {

  @JsonProperty("actions")
  List<InactiveCertificateActionConfiguration> actions;

  @JsonPOJOBuilder(withPrefix = "")
  public static class InactiveCertificateConfigurationBuilder {

  }
}