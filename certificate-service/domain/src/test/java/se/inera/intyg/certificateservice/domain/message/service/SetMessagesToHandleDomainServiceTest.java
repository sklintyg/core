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
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class SetMessagesToHandleDomainServiceTest {

  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private SetMessagesToHandleDomainService setMessagesToHandleDomainService;

  @Test
  void shallHandleMessage() {
    final var messageToHandle = complementMessageBuilder()
        .status(MessageStatus.SENT)
        .build();

    setMessagesToHandleDomainService.handle(List.of(messageToHandle));

    assertEquals(MessageStatus.HANDLED, messageToHandle.status());
  }

  @Test
  void shallSaveHandledMessage() {
    final var messageToHandle = complementMessageBuilder()
        .status(MessageStatus.SENT)
        .build();

    setMessagesToHandleDomainService.handle(List.of(messageToHandle));

    verify(messageRepository).save(messageToHandle);
  }

  @Test
  void shallHandleMessages() {
    final var messagesToHandle = List.of(
        complementMessageBuilder()
            .id(new MessageId("firstMessage"))
            .status(MessageStatus.SENT)
            .build(),
        complementMessageBuilder()
            .id(new MessageId("secondMessage"))
            .status(MessageStatus.SENT)
            .build()
    );

    setMessagesToHandleDomainService.handle(messagesToHandle);

    assertAll(
        () -> assertEquals(MessageStatus.HANDLED, messagesToHandle.get(0).status()),
        () -> assertEquals(MessageStatus.HANDLED, messagesToHandle.get(1).status())
    );
  }

  @Test
  void shallSaveMessages() {
    final var messagesToHandle = List.of(
        complementMessageBuilder()
            .id(new MessageId("firstMessage"))
            .status(MessageStatus.SENT)
            .build(),
        complementMessageBuilder()
            .id(new MessageId("secondMessage"))
            .status(MessageStatus.SENT)
            .build()
    );

    setMessagesToHandleDomainService.handle(messagesToHandle);

    verify(messageRepository).save(messagesToHandle.get(0));
    verify(messageRepository).save(messagesToHandle.get(1));
  }

  @Test
  void shallRemoveDraftAnswer() {
    final var messageToHandle = complementMessageBuilder()
        .status(MessageStatus.SENT)
        .answer(Answer.builder()
            .status(MessageStatus.DRAFT)
            .build())
        .build();

    setMessagesToHandleDomainService.handle(List.of(messageToHandle));

    assertEquals(MessageStatus.DELETED_DRAFT, messageToHandle.answer().status());
  }

  @Test
  void shallNotRemoveSentAnswer() {
    final var messageToHandle = complementMessageBuilder()
        .status(MessageStatus.SENT)
        .answer(Answer.builder()
            .status(MessageStatus.SENT)
            .build())
        .build();

    setMessagesToHandleDomainService.handle(List.of(messageToHandle));

    assertEquals(MessageStatus.SENT, messageToHandle.answer().status());
  }

}
