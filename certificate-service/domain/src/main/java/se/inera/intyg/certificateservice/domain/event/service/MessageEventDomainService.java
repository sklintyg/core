package se.inera.intyg.certificateservice.domain.event.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;

@RequiredArgsConstructor
public class MessageEventDomainService {

  private final List<MessageEventSubscriber> subscribers;

  public void publish(MessageEvent event) {
    if (event == null) {
      throw new IllegalArgumentException("Event is null and cannot be published!");
    }
    subscribers.forEach(messageEventSubscriber -> messageEventSubscriber.event(event));
  }
}
