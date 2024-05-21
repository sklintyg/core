package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.ComplementDTO.ComplementDTOBuilder;

@JsonDeserialize(builder = ComplementDTOBuilder.class)
@Value
@Builder
public class ComplementDTO {

  String questionId;
  String questionText;
  String valueId;
  String message;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ComplementDTOBuilder {

  }
}
