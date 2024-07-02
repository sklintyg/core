package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxMultipleCode.CheckboxMultipleCodeBuilder;

@JsonDeserialize(builder = CheckboxMultipleCodeBuilder.class)
@Value
@Builder
public class CheckboxMultipleCode {

  String id;
  String label;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CheckboxMultipleCodeBuilder {

  }
}
