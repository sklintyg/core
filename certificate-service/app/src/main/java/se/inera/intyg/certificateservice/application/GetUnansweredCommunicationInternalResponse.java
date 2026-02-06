package se.inera.intyg.certificateservice.application;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.GetUnansweredCommunicationInternalResponse.GetUnansweredCommunicationInternalResponseBuilder;
import se.inera.intyg.certificateservice.domain.message.model.UnansweredQAs;

@JsonDeserialize(builder = GetUnansweredCommunicationInternalResponseBuilder.class)
@Value
@Builder
public class GetUnansweredCommunicationInternalResponse {

  Map<String, UnansweredQAs> messages;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetUnansweredCommunicationInternalResponseBuilder {

  }

}
