package se.inera.intyg.certificateservice.domain.event.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;

@RequiredArgsConstructor
public class CertificateEventDomainService {

  private final List<CertificateEventSubscriber> subscribers;

  public void publish(CertificateEvent event) {
    if (event == null) {
      throw new IllegalArgumentException("Event is null and cannot be published!");
    }
    subscribers.forEach(certificateEventSubscriber -> certificateEventSubscriber.event(event));
  }
}
