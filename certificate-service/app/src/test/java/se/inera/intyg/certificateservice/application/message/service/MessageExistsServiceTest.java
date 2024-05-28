package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class MessageExistsServiceTest {

  private static final String MESSAGE_ID = "messageId";
  @Mock
  private MessageRepository messageRepository;
  @InjectMocks
  private MessageExistsService messageExistsService;

  @Test
  void shallThrowIfMessageIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> messageExistsService.exist(null));
  }

  @Test
  void shallThrowIfMessageIdIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> messageExistsService.exist(" "));
  }

  @Test
  void shallReturnCertificateExistsResponse() {
    final var messageId = new MessageId(MESSAGE_ID);
    final var expectedResponse = MessageExistsResponse.builder()
        .exists(true)
        .build();

    doReturn(true).when(messageRepository).exists(messageId);

    final var actualResponse = messageExistsService.exist(MESSAGE_ID);
    assertEquals(expectedResponse, actualResponse);
  }
}
