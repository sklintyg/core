package se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration.LimitedCertificateFunctionalityConfigurationBuilder;

@JsonDeserialize(builder = LimitedCertificateFunctionalityConfigurationBuilder.class)
@Builder
@Value
public class LimitedCertificateFunctionalityConfiguration {

  @JsonProperty("certificateType")
  String certificateType;
  @JsonProperty("version")
  List<String> version;
  @JsonProperty("configuration")
  LimitedCertificateFunctionalityActionsConfiguration configuration;

  @JsonPOJOBuilder(withPrefix = "")
  public static class LimitedCertificateFunctionalityConfigurationBuilder {

  }
}