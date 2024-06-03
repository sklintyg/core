package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.AnswerDTO.AnswerDTOBuilder;

@JsonDeserialize(builder = AnswerDTOBuilder.class)
@Value
@Builder
public class AnswerDTO {

  String id;
  String message;
  String author;
  LocalDateTime sent;
  List<String> contactInfo;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AnswerDTOBuilder {

  }
}
