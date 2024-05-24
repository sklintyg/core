package se.inera.intyg.certificateservice.application.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageService;
import se.inera.intyg.certificateservice.application.message.service.IncomingMessageService;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {


  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  private IncomingMessageService incomingMessageService;
  @Mock
  private GetCertificateMessageService getCertificateMessageService;
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
}
