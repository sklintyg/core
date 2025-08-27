package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk3226CertificateBuilder;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.ForwardCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardCertificateMessagesDomainService;

@ExtendWith(MockitoExtension.class)
class ForwardCertificateServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  CertificateActionFactory certificateActionFactory;
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  ForwardCertificateRequestValidator forwardCertificateRequestValidator;
  @Mock
  ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  ForwardCertificateMessagesDomainService forwardCertificateMessagesDomainService;
  @Mock
  ForwardCertificateDomainService forwardCertificateDomainService;
  @Mock
  CertificateConverter certificateConverter;
  @Mock
  ResourceLinkConverter resourceLinkConverter;
  @InjectMocks
  ForwardCertificateService forwardCertificateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = ForwardCertificateRequest.builder().build();
    doThrow(IllegalStateException.class).when(forwardCertificateRequestValidator).validate(
        request, CERTIFICATE_ID);

    assertThrows(IllegalStateException.class,
        () -> forwardCertificateService.forward(request, CERTIFICATE_ID));
  }

  @Test
  void shallCallForwardCertificateDomainServiceIfCertificateIsDraft() {
    final var request = ForwardCertificateRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();

    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var certificate = fk3226CertificateBuilder()
        .status(Status.DRAFT)
        .build();

    doReturn(certificate).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));
    doReturn(certificate).when(forwardCertificateDomainService)
        .forward(certificate, actionEvaluation);

    forwardCertificateService.forward(request, CERTIFICATE_ID);

    verify(forwardCertificateDomainService).forward(
        certificate, actionEvaluation
    );
  }

  @Test
  void shallCallForwardMessagesDomainServiceIfCertificateIsSigned() {
    final var request = ForwardCertificateRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();

    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var certificate = fk3226CertificateBuilder()
        .status(Status.SIGNED)
        .build();

    doReturn(certificate).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));
    doReturn(certificate).when(forwardCertificateMessagesDomainService)
        .forward(certificate, actionEvaluation);

    forwardCertificateService.forward(request, CERTIFICATE_ID);

    verify(forwardCertificateMessagesDomainService).forward(
        certificate, actionEvaluation
    );
  }

  @Test
  void shallReturnForwardCertificateResponse() {
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var certificateDTO = CertificateDTO.builder()
        .links(List.of(resourceLinkDTO))
        .build();
    final var expectedResponse = ForwardCertificateResponse.builder()
        .certificate(
            certificateDTO
        )
        .build();

    final var request = ForwardCertificateRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();

    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var certificate = mock(MedicalCertificate.class);
    doReturn(false).when(certificate).isDraft();
    doReturn(certificate).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));
    doReturn(certificate).when(forwardCertificateMessagesDomainService)
        .forward(certificate, actionEvaluation);

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));
    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);
    doReturn(certificateDTO).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);

    final var actualResult = forwardCertificateService.forward(request, CERTIFICATE_ID);

    assertEquals(expectedResponse, actualResult);
  }
}