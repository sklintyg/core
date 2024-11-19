package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DiagnosesListItem.DiagnosesListItemBuilder;

@JsonDeserialize(builder = DiagnosesListItemBuilder.class)
@Value
@Builder
public class DiagnosesListItem {

  String id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DiagnosesListItemBuilder {

  }
}
