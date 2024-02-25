package se.inera.intyg.certificateservice.domain.event.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;

class CertificateEventDomainServiceTest {

  private CertificateEventDomainService certificateEventDomainService;
  private CertificateEventSubscriber subscriberOne;
  private CertificateEventSubscriber subscriberTwo;

  @BeforeEach
  void setUp() {
    subscriberOne = mock(CertificateEventSubscriber.class);
    subscriberTwo = mock(CertificateEventSubscriber.class);
    final var subscribers = List.of(subscriberOne, subscriberTwo);

    certificateEventDomainService = new CertificateEventDomainService(subscribers);
  }

  @Test
  void shallPublishToSubscribers() {
    final var expectedEvent = CertificateEvent.builder().build();

    certificateEventDomainService.publish(expectedEvent);

    verify(subscriberOne).event(expectedEvent);
    verify(subscriberTwo).event(expectedEvent);
  }

  @Test
  void shallThrowExceptionIfEventIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> certificateEventDomainService.publish(null)
    );

    verifyNoInteractions(subscriberOne);
    verifyNoInteractions(subscriberTwo);
  }
}