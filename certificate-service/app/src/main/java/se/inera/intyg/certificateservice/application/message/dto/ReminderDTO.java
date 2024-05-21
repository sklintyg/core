package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.ReminderDTO.ReminderDTOBuilder;

@JsonDeserialize(builder = ReminderDTOBuilder.class)
@Value
@Builder
public class ReminderDTO {

  String id;
  String message;
  String author;
  LocalDateTime sent;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ReminderDTOBuilder {

  }
}
