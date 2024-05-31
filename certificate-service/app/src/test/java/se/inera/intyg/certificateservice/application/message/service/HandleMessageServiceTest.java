package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
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
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
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
      .isHandled(true)
      .build();
  private static final ActionEvaluation ACTION = ActionEvaluation.builder().build();
  private static final String MESSAGE_ID = "messageId";
  private static final String CERTIFICATE_ID = "certId";
  private static final Message MESSAGE = Message.builder()
      .certificateId(new CertificateId(CERTIFICATE_ID)).build();
  private static final Message UPDATED_MESSAGE = Message.builder().id(new MessageId("ID")).build();
  private static final Certificate CERTIFICATE = Certificate.builder().build();
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
  GetCertificateDomainService getCertificateDomainService;

  @Mock
  QuestionConverter questionConverter;

  @InjectMocks
  HandleMessageService handleMessageService;

  @BeforeEach
  void setup() {
    when(actionEvaluationFactory.create(REQUEST.getUser(), REQUEST.getUnit(), REQUEST.getCareUnit(),
        REQUEST.getCareProvider()))
        .thenReturn(ACTION);
    when(messageRepository.getById(new MessageId(MESSAGE_ID)))
        .thenReturn(MESSAGE);
    when(getCertificateDomainService.get(new CertificateId(CERTIFICATE_ID), ACTION))
        .thenReturn(CERTIFICATE);
    when(handleMessageDomainService.handle(MESSAGE, REQUEST.getIsHandled(), CERTIFICATE, ACTION))
        .thenReturn(UPDATED_MESSAGE);
    when(questionConverter.convert(UPDATED_MESSAGE, Collections.emptyList()))
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