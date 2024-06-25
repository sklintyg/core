package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerResponse.DeleteAnswerResponseBuilder;

@JsonDeserialize(builder = DeleteAnswerResponseBuilder.class)
@Value
@Builder
public class DeleteAnswerResponse {

  QuestionDTO question;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DeleteAnswerResponseBuilder {

  }
}
