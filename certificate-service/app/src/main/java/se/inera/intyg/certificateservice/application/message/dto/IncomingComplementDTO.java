package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.IncomingComplementDTO.IncomingComplementDTOBuilder;

@JsonDeserialize(builder = IncomingComplementDTOBuilder.class)
@Value
@Builder
public class IncomingComplementDTO {

  String questionId;
  Integer instance;
  String content;

  @JsonPOJOBuilder(withPrefix = "")
  public static class IncomingComplementDTOBuilder {

  }
}
