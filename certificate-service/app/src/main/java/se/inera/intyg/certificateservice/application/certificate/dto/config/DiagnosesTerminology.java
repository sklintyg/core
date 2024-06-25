package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DiagnosesTerminology.DiagnosesTerminologyBuilder;

@JsonDeserialize(builder = DiagnosesTerminologyBuilder.class)
@Value
@Builder
public class DiagnosesTerminology {

  String id;
  String label;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DiagnosesTerminologyBuilder {

  }
}
