package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.AlertDTO.AlertBuilder;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AlertType;

@JsonDeserialize(builder = AlertBuilder.class)
@Value
@Builder
public class AlertDTO {

  AlertType type;
  String text;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AlertBuilder {

  }
}