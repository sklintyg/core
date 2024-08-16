package se.inera.intyg.certificateservice.application.certificate.service;

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
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateEventDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateEventConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificateEventsRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateEventsDomainService;

@ExtendWith(MockitoExtension.class)
class GetCertificateEventsServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";

  @Mock
  private GetCertificateEventsRequestValidator getCertificateEventsRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private GetCertificateEventsDomainService getCertificateEventsDomainService;
  @Mock
  private CertificateEventConverter certificateEventConverter;
  @InjectMocks
  private GetCertificateEventsService getCertificateEventsService;

  private static final GetCertificateEventsRequest REQUEST = GetCertificateEventsRequest.builder()
      .user(AJLA_DOCTOR_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .build();

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = GetCertificateEventsRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(getCertificateEventsRequestValidator)
        .validate(request, CERTIFICATE_ID);

    assertThrows(IllegalArgumentException.class,
        () -> getCertificateEventsService.get(request, CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnCertificateEventsResponse() {
    final var event = CertificateEventDTO.builder().build();
    final var originalEvent = CertificateEvent.builder().build();

    final var expectedResponse = GetCertificateEventsResponse.builder()
        .events(
            List.of(event)
        )
        .build();
    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        REQUEST.getUser(),
        REQUEST.getUnit(),
        REQUEST.getCareUnit(),
        REQUEST.getCareProvider()
    );

    doReturn(List.of(originalEvent)).when(getCertificateEventsDomainService).get(
        new CertificateId(CERTIFICATE_ID),
        actionEvaluation
    );

    doReturn(event).when(certificateEventConverter)
        .convert(originalEvent);

    final var actualResult = getCertificateEventsService.get(
        REQUEST, CERTIFICATE_ID
    );

    assertEquals(expectedResponse, actualResult);
  }
}
