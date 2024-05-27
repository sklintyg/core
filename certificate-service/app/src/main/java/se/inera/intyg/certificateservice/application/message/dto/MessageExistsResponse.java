package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse.MessageExistsResponseBuilder;

@JsonDeserialize(builder = MessageExistsResponseBuilder.class)
@Value
@Builder
public class MessageExistsResponse {

  boolean exists;

  @JsonPOJOBuilder(withPrefix = "")
  public static class MessageExistsResponseBuilder {

  }
}
