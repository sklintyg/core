package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerResponse.SendAnswerResponseBuilder;

@JsonDeserialize(builder = SendAnswerResponseBuilder.class)
@Value
@Builder
public class SendAnswerResponse {

  QuestionDTO question;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SendAnswerResponseBuilder {

  }
}
