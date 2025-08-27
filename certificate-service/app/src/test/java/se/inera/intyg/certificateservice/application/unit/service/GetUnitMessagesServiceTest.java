package se.inera.intyg.certificateservice.application.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.MessagesRequestFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.MessagesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.application.unit.service.validator.GetUnitMessagesRequestValidator;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.common.model.MessagesResponse;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitMessagesDomainService;

@ExtendWith(MockitoExtension.class)
class GetUnitMessagesServiceTest {

  @Mock
  private GetUnitMessagesRequestValidator getUnitMessagesRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private MessagesRequestFactory messagesRequestFactory;
  @Mock
  private GetUnitMessagesDomainService getUnitMessagesDomainService;
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
  @Mock
  private CertificateConverter certificateConverter;
  @Mock
  private QuestionConverter questionConverter;
  @InjectMocks
  private GetUnitMessagesService getUnitMessagesService;

  private static final MessagesRequest MESSAGES_REQUEST = MessagesRequest.builder()
      .build();
  private static final MessagesQueryCriteriaDTO CRITERIA_DTO = MessagesQueryCriteriaDTO.builder()
      .build();
  private static final CertificateId CERTIFICATE_ID = new CertificateId("CERT_ID");

  @Test
  void shallThrowIfInvalidRequest() {
    final var request = GetUnitMessagesRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(getUnitMessagesRequestValidator)
        .validate(request);

    assertThrows(IllegalArgumentException.class, () -> getUnitMessagesService.get(request));
  }

  @Test
  void shallReturnGetUnitMessagesResponse() {
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var certificateDTO = CertificateDTO.builder()
        .links(List.of(resourceLinkDTO))
        .build();
    final var questionDTO = QuestionDTO.builder().build();
    final var expectedResponse = GetUnitMessagesResponse.builder()
        .certificates(
            List.of(certificateDTO)
        )
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

    doReturn(MESSAGES_REQUEST).when(messagesRequestFactory).create(CRITERIA_DTO);

    final var message = mock(Message.class);
    final var certificate = mock(MedicalCertificate.class);
    final var messageResponse = MessagesResponse.builder()
        .messages(List.of(message))
        .certificates(List.of(certificate))
        .build();

    when(message.certificateId())
        .thenReturn(CERTIFICATE_ID);
    when(certificate.id())
        .thenReturn(CERTIFICATE_ID);
    doReturn(messageResponse).when(getUnitMessagesDomainService).get(
        MESSAGES_REQUEST,
        actionEvaluation
    );

    final var messageActions = List.of(MessageActionLink.builder().build());
    when(message.actions(actionEvaluation, certificate))
        .thenReturn(messageActions);

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);
    doReturn(certificateDTO).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);
    doReturn(questionDTO).when(questionConverter)
        .convert(message, messageActions);

    final var actualResult = getUnitMessagesService.get(
        GetUnitMessagesRequest.builder()
            .user(AJLA_DOCTOR_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .messagesQueryCriteria(CRITERIA_DTO)
            .build()
    );

    assertEquals(expectedResponse, actualResult);
  }
}