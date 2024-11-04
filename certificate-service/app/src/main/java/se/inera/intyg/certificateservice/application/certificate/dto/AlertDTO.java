package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.AlertDTO.AlertBuilder;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;

@JsonDeserialize(builder = AlertBuilder.class)
@Value
@Builder
public class AlertDTO {

  MessageLevel type;
  String text;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AlertBuilder {

  }
}