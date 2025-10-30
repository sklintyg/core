package se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessUnitConfiguration.CertificateAccessUnitConfigurationBuilder;

@JsonDeserialize(builder = CertificateAccessUnitConfigurationBuilder.class)
@Builder
@Value
public class CertificateAccessUnitConfiguration {

  @JsonProperty("label")
  String label;
  @JsonProperty("type")
  String type;
  @JsonProperty("fromDateTime")
  LocalDateTime fromDateTime;
  @JsonProperty("toDateTime")
  LocalDateTime toDateTime;
  @JsonProperty("careProviders")
  @Builder.Default
  List<String> careProviders = Collections.emptyList();
  @JsonProperty("careUnit")
  @Builder.Default
  List<String> careUnit = Collections.emptyList();
  @JsonProperty("issuedOnUnit")
  @Builder.Default
  List<String> issuedOnUnit = Collections.emptyList();

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateAccessUnitConfigurationBuilder {

  }
}