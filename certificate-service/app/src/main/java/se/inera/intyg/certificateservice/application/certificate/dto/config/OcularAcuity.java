package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.OcularAcuity.OcularAcuityBuilder;

@JsonDeserialize(builder = OcularAcuityBuilder.class)
@Value
@Builder
public class OcularAcuity {

  String label;
  String withCorrectionId;
  String withoutCorrectionId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class OcularAcuityBuilder {

  }
}