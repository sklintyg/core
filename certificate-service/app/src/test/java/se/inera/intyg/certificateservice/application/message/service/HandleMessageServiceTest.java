package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.HandleMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.HandleMessageDomainService;

@ExtendWith(MockitoExtension.class)
class HandleMessageServiceTest {

  private static final HandleMessageRequest REQUEST = HandleMessageRequest.builder()
      .unit(UnitDTO.builder().build())
      .user(UserDTO.builder().build())
      .careUnit(UnitDTO.builder().build())
      .careProvider(UnitDTO.builder().build())
      .handled(true)
      .build();
  private static final ActionEvaluation ACTION = ActionEvaluation.builder().build();
  private static final String MESSAGE_ID = "messageId";
  private static final String CERTIFICATE_ID = "certId";
  private static final Message MESSAGE = Message.builder()
      .certificateId(new CertificateId(CERTIFICATE_ID)).build();
  private static final QuestionDTO CONVERTED_QUESTION = QuestionDTO.builder().build();

  @Mock
  HandleMessageRequestValidator handleMessageRequestValidator;

  @Mock
  HandleMessageDomainService handleMessageDomainService;

  @Mock
  ActionEvaluationFactory actionEvaluationFactory;

  @Mock
  MessageRepository messageRepository;

  @Mock
  CertificateRepository certificateRepository;

  @Mock
  QuestionConverter questionConverter;

  @InjectMocks
  HandleMessageService handleMessageService;

  @BeforeEach
  void setup() {
    final var certificate = mock(Certificate.class);
    when(actionEvaluationFactory.create(REQUEST.getUser(), REQUEST.getUnit(), REQUEST.getCareUnit(),
        REQUEST.getCareProvider()))
        .thenReturn(ACTION);
    when(messageRepository.getById(new MessageId(MESSAGE_ID)))
        .thenReturn(MESSAGE);
    when(certificateRepository.getById(new CertificateId(CERTIFICATE_ID)))
        .thenReturn(certificate);
    when(handleMessageDomainService.handle(MESSAGE, REQUEST.getHandled(), certificate, ACTION))
        .thenReturn(COMPLEMENT_MESSAGE);
    when(questionConverter.convert(
        COMPLEMENT_MESSAGE,
        COMPLEMENT_MESSAGE.actions(ACTION, certificate)))
        .thenReturn(CONVERTED_QUESTION);
  }

  @Test
  void shouldValidateRequest() {
    handleMessageService.handle(REQUEST, MESSAGE_ID);
    verify(handleMessageRequestValidator).validate(REQUEST, MESSAGE_ID);
  }

  @Test
  void shouldReturnConvertedQuestionOfUpdatedMessage() {
    final var response = handleMessageService.handle(REQUEST, MESSAGE_ID);

    assertEquals(CONVERTED_QUESTION, response.getQuestion());
  }
}