package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class SetMessagesToUnhandledDomainServiceTest {

  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private SetMessagesToUnhandledDomainService setMessagesToHandleDomainService;

  @Test
  void shallUnhandleMessage() {
    final var messageToHandle = complementMessageBuilder()
        .status(MessageStatus.HANDLED)
        .build();

    setMessagesToHandleDomainService.unhandled(List.of(messageToHandle));

    assertEquals(MessageStatus.SENT, messageToHandle.status());
  }

  @Test
  void shallSaveUnhandledMessage() {
    final var messageToHandle = complementMessageBuilder()
        .status(MessageStatus.HANDLED)
        .build();

    setMessagesToHandleDomainService.unhandled(List.of(messageToHandle));

    verify(messageRepository).save(messageToHandle);
  }

  @Test
  void shallUnhandleMessages() {
    final var messagesToHandle = List.of(
        complementMessageBuilder()
            .id(new MessageId("firstMessage"))
            .status(MessageStatus.HANDLED)
            .build(),
        complementMessageBuilder()
            .id(new MessageId("secondMessage"))
            .status(MessageStatus.HANDLED)
            .build()
    );

    setMessagesToHandleDomainService.unhandled(messagesToHandle);

    assertAll(
        () -> assertEquals(MessageStatus.SENT, messagesToHandle.getFirst().status()),
        () -> assertEquals(MessageStatus.SENT, messagesToHandle.getLast().status())
    );
  }

  @Test
  void shallSaveMessages() {
    final var messagesToHandle = List.of(
        complementMessageBuilder()
            .id(new MessageId("firstMessage"))
            .status(MessageStatus.HANDLED)
            .build(),
        complementMessageBuilder()
            .id(new MessageId("secondMessage"))
            .status(MessageStatus.HANDLED)
            .build()
    );

    setMessagesToHandleDomainService.unhandled(messagesToHandle);

    verify(messageRepository).save(messagesToHandle.get(0));
    verify(messageRepository).save(messagesToHandle.get(1));
  }
}