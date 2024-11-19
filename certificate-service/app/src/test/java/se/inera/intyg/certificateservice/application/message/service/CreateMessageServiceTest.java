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
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.CreateMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.CreateMessageDomainService;

@ExtendWith(MockitoExtension.class)
class CreateMessageServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String MESSAGE = "message";
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CreateMessageRequestValidator createMessageRequestValidator;
  @Mock
  ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  CreateMessageDomainService createMessageDomainService;
  @Mock
  QuestionConverter questionConverter;
  @InjectMocks
  CreateMessageService createMessageService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = CreateMessageRequest.builder().build();
    doThrow(IllegalStateException.class).when(createMessageRequestValidator).validate(
        request, CERTIFICATE_ID);

    assertThrows(IllegalStateException.class,
        () -> createMessageService.create(request, CERTIFICATE_ID));
  }

  @Test
  void shallReturnCreateMessageResponseWithCreatedQuestion() {
    final var request = CreateMessageRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .patient(ATHENA_REACT_ANDERSSON_DTO)
        .questionType(QuestionTypeDTO.CONTACT)
        .message(MESSAGE)
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();

    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        ATHENA_REACT_ANDERSSON_DTO,
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    doReturn(FK3226_CERTIFICATE).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));
    doReturn(CONTACT_MESSAGE).when(createMessageDomainService).create(
        FK3226_CERTIFICATE,
        actionEvaluation,
        MessageType.CONTACT,
        new Content(MESSAGE)
    );

    final var questionDTO = QuestionDTO.builder().build();
    doReturn(questionDTO).when(questionConverter)
        .convert(CONTACT_MESSAGE, CONTACT_MESSAGE.actions(actionEvaluation, FK3226_CERTIFICATE));

    final var expectedResponse = CreateMessageResponse.builder()
        .question(questionDTO)
        .build();

    final var actualResponse = createMessageService.create(request, CERTIFICATE_ID);
    assertEquals(expectedResponse, actualResponse);
  }
}
