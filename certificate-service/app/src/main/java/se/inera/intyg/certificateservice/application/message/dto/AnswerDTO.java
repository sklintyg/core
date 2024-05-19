package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.AnswerDTO.AnswerBuilder;

@JsonDeserialize(builder = AnswerBuilder.class)
@Value
@Builder
public class AnswerDTO {

  String id;
  String message;
  String author;
  LocalDateTime sent;
  String[] contactInfo;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AnswerBuilder {

  }
}
