package se.inera.intyg.certificateservice.domain.event.service;

import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;

public interface CertificateEventSubscriber {

  void event(CertificateEvent event);
}
