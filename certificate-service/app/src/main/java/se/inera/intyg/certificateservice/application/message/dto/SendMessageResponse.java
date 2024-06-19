package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageResponse.SendMessageResponseBuilder;

@JsonDeserialize(builder = SendMessageResponseBuilder.class)
@Value
@Builder
public class SendMessageResponse {

  QuestionDTO question;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SendMessageResponseBuilder {

  }
}
