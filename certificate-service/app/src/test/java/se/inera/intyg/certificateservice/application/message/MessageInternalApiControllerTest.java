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
import se.inera.intyg.certificateservice.application.GetUnansweredCommunicationInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetUnansweredCommunicationInternalRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageInternalResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetMessageInternalXmlResponse;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.service.GetCertificateMessageInternalService;
import se.inera.intyg.certificateservice.application.message.service.GetMessageInternalXmlService;
import se.inera.intyg.certificateservice.application.message.service.GetUnansweredMessageInternalService;
import se.inera.intyg.certificateservice.domain.message.model.UnansweredQAs;

@ExtendWith(MockitoExtension.class)
class MessageInternalApiControllerTest {

  @Mock
  private GetMessageInternalXmlService getMessageInternalXmlService;
  @Mock
  private GetCertificateMessageInternalService getCertificateMessageInternalService;
  @Mock
  private GetUnansweredMessageInternalService getUnansweredMessageInternalService;
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
  void shallReturnGetUnansweredCommunicationInternalResponse() {
    final var patientIds = List.of("patient-1", "patient-2");
    final var maxDays = 7;
    final var request = GetUnansweredCommunicationInternalRequest.builder()
        .patientId(patientIds)
        .maxDaysOfUnansweredCommunication(maxDays)
        .build();

    final var expectedResult = GetUnansweredCommunicationInternalResponse.builder()
        .messages(Map.of("certificateId", UnansweredQAs.builder().complement(1).build()))
        .build();

    doReturn(expectedResult).when(getUnansweredMessageInternalService).get(patientIds, maxDays);

    final var actualResult = messageInternalApiController.getUnansweredCommunicationMessages(
        request);

    assertEquals(expectedResult, actualResult);
  }


}
