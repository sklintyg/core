package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.INCOMING_COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO;
import se.inera.intyg.certificateservice.application.message.service.converter.MessageConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.IncomingMessageValidator;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveComplementMessageDomainService;

@ExtendWith(MockitoExtension.class)
class IncomingMessageServiceTest {

  @Mock
  private IncomingMessageValidator incomingMessageValidator;

  @Mock
  private MessageConverter messageConverter;

  @Mock
  private ReceiveComplementMessageDomainService receiveComplementMessageDomainService;

  @InjectMocks
  private IncomingMessageService incomingMessageService;

  @Test
  void shallThrowExceptionIfRequestIsNotValid() {
    final var invalidMessage = IncomingMessageRequest.builder().build();

    doThrow(new IllegalArgumentException()).when(incomingMessageValidator)
        .validate(invalidMessage);

    assertThrows(IllegalArgumentException.class,
        () -> incomingMessageService.receive(invalidMessage)
    );
  }

  @Test
  void shallReceiveComplementMessages() {
    doReturn(COMPLEMENT_MESSAGE).when(messageConverter).convert(INCOMING_COMPLEMENT_MESSAGE);
    incomingMessageService.receive(INCOMING_COMPLEMENT_MESSAGE);
    verify(receiveComplementMessageDomainService).receive(COMPLEMENT_MESSAGE);
  }

  @Test
  void shallThrowExceptionIfMessageTypeIsNotHandled() {
    final var notHandledMessage = incomingComplementMessageBuilder()
        .type(MessageTypeDTO.AVSTMN)
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> incomingMessageService.receive(notHandledMessage)
    );
  }
}