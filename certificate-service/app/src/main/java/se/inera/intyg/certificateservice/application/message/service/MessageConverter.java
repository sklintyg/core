package se.inera.intyg.certificateservice.application.message.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.domain.message.model.Message;

@Component
public class MessageConverter {

  public Message convert(IncomingMessageRequest incomingMessageRequest) {
    return null;
  }
}
