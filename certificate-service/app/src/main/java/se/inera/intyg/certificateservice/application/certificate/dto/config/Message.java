package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Message.MessageBuilder;

@JsonDeserialize(builder = MessageBuilder.class)
@Value
@Builder
public class Message {

  String content;
  MessageLevel level;

  @JsonPOJOBuilder(withPrefix = "")
  public static class MessageBuilder {

  }
}
