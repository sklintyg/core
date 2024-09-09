package se.inera.intyg.certificateservice.domain.unitaccess.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.unitaccess.dto.CertificateAccessConfiguration.CertificateAccessConfigurationBuilder;

@JsonInclude
@JsonDeserialize(builder = CertificateAccessConfigurationBuilder.class)
@Builder
@Value
public class CertificateAccessConfiguration {

  @JsonProperty("certificateType")
  String certificateType;
  @JsonProperty("configuration")
  List<CertificateUnitAccessConfiguration> configuration;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateAccessConfigurationBuilder {

  }
}