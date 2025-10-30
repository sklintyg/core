package se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration.CertificateAccessConfigurationBuilder;

@JsonDeserialize(builder = CertificateAccessConfigurationBuilder.class)
@Builder
@Value
public class CertificateAccessConfiguration {

  @JsonProperty("certificateType")
  String certificateType;
  @JsonProperty("configuration")
  List<CertificateAccessUnitConfiguration> configuration;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateAccessConfigurationBuilder {

  }
}