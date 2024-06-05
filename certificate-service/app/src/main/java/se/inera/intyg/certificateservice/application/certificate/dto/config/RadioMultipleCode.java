package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.RadioMultipleCode.RadioMultipleCodeBuilder;

@JsonDeserialize(builder = RadioMultipleCodeBuilder.class)
@Value
@Builder
public class RadioMultipleCode {

  String id;
  String label;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RadioMultipleCodeBuilder {

  }
}
