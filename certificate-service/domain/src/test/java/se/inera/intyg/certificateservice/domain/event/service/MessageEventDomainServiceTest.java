package se.inera.intyg.certificateservice.domain.event.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;

class MessageEventDomainServiceTest {

  private MessageEventDomainService messageEventDomainService;
  private MessageEventSubscriber subscriberOne;
  private MessageEventSubscriber subscriberTwo;

  @BeforeEach
  void setUp() {
    subscriberOne = mock(MessageEventSubscriber.class);
    subscriberTwo = mock(MessageEventSubscriber.class);
    final var subscribers = List.of(subscriberOne, subscriberTwo);

    messageEventDomainService = new MessageEventDomainService(subscribers);
  }

  @Test
  void shallPublishToSubscribers() {
    final var expectedEvent = MessageEvent.builder().build();

    messageEventDomainService.publish(expectedEvent);

    verify(subscriberOne).event(expectedEvent);
    verify(subscriberTwo).event(expectedEvent);
  }

  @Test
  void shallThrowExceptionIfEventIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> messageEventDomainService.publish(null)
    );

    verifyNoInteractions(subscriberOne);
    verifyNoInteractions(subscriberTwo);
  }
}