package se.inera.intyg.certificateservice.domain.event.service;

import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;

public interface MessageEventSubscriber {

  void event(MessageEvent event);
}
