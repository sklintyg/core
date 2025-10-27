package se.inera.intyg.certificateservice.domain.configuration.inactive.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.CertificateInactiveConfiguration.CertificateInactiveConfigurationBuilder;

@JsonInclude
@JsonDeserialize(builder = CertificateInactiveConfigurationBuilder.class)
@Builder
@Value
public class CertificateInactiveConfiguration {

  @JsonProperty("certificateType")
  String certificateType;
  @JsonProperty("version")
  String version;
  @JsonProperty("configuration")
  InactiveCertificateConfiguration configuration;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateInactiveConfigurationBuilder {

  }
}