package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.GetCertificateMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.service.GetCertificateMessageDomainService;

@ExtendWith(MockitoExtension.class)
class GetCertificateMessageServiceTest {


  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private GetCertificateMessageRequestValidator messageRequestValidator;
  @Mock
  private GetCertificateMessageDomainService getCertificateMessageDomainService;
  @Mock
  private QuestionConverter questionConverter;
  @InjectMocks
  private GetCertificateMessageService getCertificateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = GetCertificateMessageRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(messageRequestValidator)
        .validate(request, CERTIFICATE_ID);
    assertThrows(IllegalArgumentException.class,
        () -> getCertificateService.get(request, CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnResponseWithCertificate() {
    final var questionDTO = QuestionDTO.builder().build();
    final var expectedResponse = GetCertificateMessageResponse.builder()
        .questions(
            List.of(questionDTO)
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();

    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var message = Message.builder().build();
    doReturn(List.of(message)).when(getCertificateMessageDomainService).get(
        actionEvaluation,
        new CertificateId(CERTIFICATE_ID)
    );

    doReturn(questionDTO).when(questionConverter)
        .convert(message);

    final var actualResult = getCertificateService.get(
        GetCertificateMessageRequest.builder()
            .user(AJLA_DOCTOR_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .build(),
        CERTIFICATE_ID
    );

    assertEquals(expectedResponse, actualResult);
  }
}
