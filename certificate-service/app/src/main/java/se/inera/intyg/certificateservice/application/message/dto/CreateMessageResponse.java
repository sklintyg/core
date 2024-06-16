package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageResponse.CreateMessageResponseBuilder;

@JsonDeserialize(builder = CreateMessageResponseBuilder.class)
@Value
@Builder
public class CreateMessageResponse {

  QuestionDTO question;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CreateMessageResponseBuilder {

  }
}
