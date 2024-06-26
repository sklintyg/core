package se.inera.intyg.certificateservice.application.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.DeleteAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.DeleteMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendAnswerResponse;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.CreateMessageService;
import se.inera.intyg.certificateservice.application.message.service.DeleteAnswerService;
import se.inera.intyg.certificateservice.application.message.service.DeleteMessageService;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateFromMessageService;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageService;
import se.inera.intyg.certificateservice.application.message.service.HandleMessageService;
import se.inera.intyg.certificateservice.application.message.service.IncomingMessageService;
import se.inera.intyg.certificateservice.application.message.service.MessageExistsService;
import se.inera.intyg.certificateservice.application.message.service.SaveAnswerService;
import se.inera.intyg.certificateservice.application.message.service.SaveMessageService;
import se.inera.intyg.certificateservice.application.message.service.SendAnswerService;
import se.inera.intyg.certificateservice.application.message.service.SendMessageService;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {


  private static final String CERTIFICATE_ID = "certificateId";
  private static final String MESSAGE_ID = "messageId";
  private static final CertificateDTO CERTIFICATE = CertificateDTO.builder().build();
  @Mock
  private SendAnswerService sendAnswerService;
  @Mock
  private DeleteAnswerService deleteAnswerService;
  @Mock
  private SaveAnswerService saveAnswerService;
  @Mock
  private SendMessageService sendMessageService;
  @Mock
  private DeleteMessageService deleteMessageService;
  @Mock
  private SaveMessageService saveMessageService;
  @Mock
  private CreateMessageService createMessageService;
  @Mock
  private GetCertificateFromMessageService getCertificateFromMessageService;
  @Mock
  private MessageExistsService messageExistsService;
  @Mock
  private IncomingMessageService incomingMessageService;
  @Mock
  private GetCertificateMessageService getCertificateMessageService;
  @Mock
  private HandleMessageService handleMessageService;
  @InjectMocks
  private MessageController messageController;

  @Nested
  class ReceiveMessageTest {

    @Test
    void shallHandleIncomingMessageRequest() {
      final var request = IncomingMessageRequest.builder().build();
      messageController.receiveMessage(request);
      verify(incomingMessageService).receive(request);
    }
  }

  @Nested
  class GetMessagesForCertificateTest {

    @Test
    void shallReturnGetMessagesForCertficiateResponse() {
      final var expectedResponse = GetCertificateMessageResponse.builder().build();
      final var request = GetCertificateMessageRequest.builder().build();

      doReturn(expectedResponse).when(getCertificateMessageService)
          .get(request, CERTIFICATE_ID);

      final var actualResponse = messageController.getMessagesForCertificate(request,
          CERTIFICATE_ID);

      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class FindExistingMessage {

    @Test
    void shallReturnMessageExistsResponse() {
      final var expectedResponse = MessageExistsResponse.builder()
          .exists(true)
          .build();
      doReturn(expectedResponse).when(messageExistsService).exist(MESSAGE_ID);

      final var actualResponse = messageController.findExistingMessage(MESSAGE_ID);
      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class GetCertificateFromMessage {

    @Test
    void shallReturnGetCertificateFromMessageResponse() {
      final var expectedResponse = GetCertificateFromMessageResponse.builder()
          .certificate(CERTIFICATE)
          .build();
      final var request = GetCertificateFromMessageRequest.builder().build();
      doReturn(expectedResponse).when(getCertificateFromMessageService).get(request, MESSAGE_ID);

      final var actualResponse = messageController.getCertificateFromMessage(request, MESSAGE_ID);
      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class HandleMessage {


    @Test
    void shallReturnResponseWhenHandlingMessage() {
      final var expectedResponse = HandleMessageResponse.builder()
          .question(QuestionDTO.builder().build())
          .build();
      final var request = HandleMessageRequest.builder().build();
      when(handleMessageService.handle(request, "messageId"))
          .thenReturn(expectedResponse);

      final var actualResponse = messageController.handleMessage(request, "messageId");

      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class CreateMessage {

    @Test
    void shallReturnCreateMessageResponse() {
      final var expectedResponse = CreateMessageResponse.builder()
          .question(QuestionDTO.builder().build())
          .build();

      final var request = CreateMessageRequest.builder().build();
      doReturn(expectedResponse).when(createMessageService)
          .create(request, CERTIFICATE_ID);

      final var actualResponse = messageController.createMessage(request, CERTIFICATE_ID);
      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class SaveMessage {

    @Test
    void shallReturnSaveMessageResponse() {
      final var expectedResponse = SaveMessageResponse.builder()
          .question(QuestionDTO.builder().build())
          .build();

      final var request = SaveMessageRequest.builder().build();
      doReturn(expectedResponse).when(saveMessageService)
          .save(request, MESSAGE_ID);

      final var actualResponse = messageController.saveMessage(request, MESSAGE_ID);
      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class DeleteMessage {

    @Test
    void shallCallDeleteMessageService() {
      final var request = DeleteMessageRequest.builder().build();

      messageController.deleteMessage(request, MESSAGE_ID);

      verify(deleteMessageService).delete(request, MESSAGE_ID);
    }
  }

  @Nested
  class SendMessage {

    @Test
    void shallReturnSendMessageResponse() {
      final var request = SendMessageRequest.builder().build();

      final var expectedResponse = SendMessageResponse.builder().build();
      doReturn(expectedResponse).when(sendMessageService).send(request, MESSAGE_ID);

      final var actualResponse = messageController.sendMessage(request, MESSAGE_ID);
      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class SaveAnswer {

    @Test
    void shallReturnSaveAnswerResponse() {
      final var request = SaveAnswerRequest.builder().build();

      final var expectedResponse = SaveAnswerResponse.builder().build();
      doReturn(expectedResponse).when(saveAnswerService).save(request, MESSAGE_ID);

      final var actualResponse = messageController.saveAnswer(request, MESSAGE_ID);
      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class DeleteAnswer {

    @Test
    void shallReturnDeleteAnswerResponse() {
      final var request = DeleteAnswerRequest.builder().build();

      final var expectedResponse = DeleteAnswerResponse.builder().build();
      doReturn(expectedResponse).when(deleteAnswerService).delete(request, MESSAGE_ID);

      final var actualResponse = messageController.deleteAnswer(request, MESSAGE_ID);
      assertEquals(expectedResponse, actualResponse);
    }
  }

  @Nested
  class SendAnswer {

    @Test
    void shallReturnSendAnswerResponse() {
      final var request = SendAnswerRequest.builder().build();

      final var expectedResponse = SendAnswerResponse.builder().build();
      doReturn(expectedResponse).when(sendAnswerService).send(request, MESSAGE_ID);

      final var actualResponse = messageController.sendAnswer(request, MESSAGE_ID);
      assertEquals(expectedResponse, actualResponse);
    }

  }
}
