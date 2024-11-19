package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.CONTACT_MESSAGE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.SendMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.SendMessageDomainService;

@ExtendWith(MockitoExtension.class)
class SendMessageServiceTest {

  private static final String MESSAGE_ID = "messageId";
  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  MessageRepository messageRepository;
  @Mock
  SendMessageRequestValidator sendMessageRequestValidator;
  @Mock
  ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  SendMessageDomainService sendMessageDomainService;
  @Mock
  QuestionConverter questionConverter;
  @InjectMocks
  SendMessageService sendMessageService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = SendMessageRequest.builder().build();
    doThrow(IllegalStateException.class).when(sendMessageRequestValidator).validate(
        request, MESSAGE_ID);

    assertThrows(IllegalStateException.class,
        () -> sendMessageService.send(request, MESSAGE_ID));
  }

  @Test
  void shallReturnSendMessageResponseWithSentQuestion() {
    final var request = SendMessageRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .patient(ATHENA_REACT_ANDERSSON_DTO)
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();

    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        ATHENA_REACT_ANDERSSON_DTO,
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var message = Message.builder()
        .certificateId(new CertificateId(CERTIFICATE_ID))
        .build();

    doReturn(message).when(messageRepository).getById(new MessageId(MESSAGE_ID));
    doReturn(FK3226_CERTIFICATE).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));
    doReturn(CONTACT_MESSAGE).when(sendMessageDomainService).send(
        message, FK3226_CERTIFICATE, actionEvaluation
    );

    final var questionDTO = QuestionDTO.builder().build();
    doReturn(questionDTO).when(questionConverter)
        .convert(CONTACT_MESSAGE, CONTACT_MESSAGE.actions(actionEvaluation, FK3226_CERTIFICATE));

    final var expectedResponse = SendMessageResponse.builder()
        .question(questionDTO)
        .build();

    final var actualResponse = sendMessageService.send(request, MESSAGE_ID);
    assertEquals(expectedResponse, actualResponse);
  }
}
