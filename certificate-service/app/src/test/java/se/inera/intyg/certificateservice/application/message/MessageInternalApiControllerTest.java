package se.inera.intyg.certificateservice.application.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSentInternalRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageInternalResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetSentMessagesCountResponse;
import se.inera.intyg.certificateservice.application.message.dto.MessageCount;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageInternalService;
import se.inera.intyg.certificateservice.application.message.service.GetMessageInternalXmlService;
import se.inera.intyg.certificateservice.application.message.service.GetSentMessageCountInternalService;

@ExtendWith(MockitoExtension.class)
class MessageInternalApiControllerTest {

  @Mock
  private GetMessageInternalXmlService getMessageInternalXmlService;
  @Mock
  private GetCertificateMessageInternalService getCertificateMessageInternalService;
  @Mock
  private GetSentMessageCountInternalService getSentMessageCountInternalService;
  @InjectMocks
  private MessageInternalApiController messageInternalApiController;

  @Test
  void shallReturnGetMessageInternalXmlResponse() {
    final var expectedResult = GetMessageInternalXmlResponse.builder()
        .xml("XML")
        .build();

    doReturn(expectedResult).when(getMessageInternalXmlService).get(MESSAGE_ID);

    final var actualResult = messageInternalApiController.getMessageXml(MESSAGE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateMessageInternalResponse() {
    final var expectedResult = GetCertificateMessageInternalResponse.builder()
        .questions(
            List.of(QuestionDTO.builder().build())
        )
        .build();

    doReturn(expectedResult).when(getCertificateMessageInternalService).get(CERTIFICATE_ID);

    final var actualResult = messageInternalApiController.getMessagesForCertificate(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetSentInternalResponse() {
    final var patientIds = List.of("patient-1", "patient-2");
    final var maxDays = 7;
    final var request = GetSentInternalRequest.builder()
        .patientIdList(patientIds)
        .maxDays(maxDays)
        .build();

    final var expectedResult = new GetSentMessagesCountResponse(
        Map.of("certificateId", new MessageCount(1, 0)));

    doReturn(expectedResult).when(getSentMessageCountInternalService).get(patientIds, maxDays);

    final var actualResult = messageInternalApiController.getSentMessagesCount(request);

    assertEquals(expectedResult, actualResult);
  }


}
