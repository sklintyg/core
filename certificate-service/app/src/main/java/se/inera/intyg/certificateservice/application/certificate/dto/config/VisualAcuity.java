package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.VisualAcuity.VisualAcuityBuilder;

@JsonDeserialize(builder = VisualAcuityBuilder.class)
@Value
@Builder
public class VisualAcuity {

  String label;
  String withCorrectionId;
  String withoutCorrectionId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class VisualAcuityBuilder {

  }
}