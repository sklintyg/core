package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.RenewCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.RenewCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@ExtendWith(MockitoExtension.class)
class RenewCertificateServiceTest {

  @Mock
  private RenewCertificateRequestValidator renewCertificateRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private RenewCertificateDomainService renewCertificateDomainService;
  @Mock
  private CertificateConverter certificateConverter;
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
  @InjectMocks
  private RenewCertificateService renewCertificateService;

  private static final String CERTIFICATE_ID = "certificateId";
  private static final RenewCertificateRequest RENEW_CERTIFICATE_REQUEST = RenewCertificateRequest.builder()
      .user(AJLA_DOCTOR_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .patient(ATHENA_REACT_ANDERSSON_DTO)
      .externalReference(EXTERNAL_REF)
      .build();

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = RenewCertificateRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(renewCertificateRequestValidator)
        .validate(request, CERTIFICATE_ID);

    assertThrows(IllegalArgumentException.class,
        () -> renewCertificateService.renew(request, CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnResponseWithNewCertificate() {
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var expectedResponse = RenewCertificateResponse.builder()
        .certificate(
            CertificateDTO.builder()
                .links(List.of(resourceLinkDTO))
                .build()
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        RENEW_CERTIFICATE_REQUEST.getPatient(),
        RENEW_CERTIFICATE_REQUEST.getUser(),
        RENEW_CERTIFICATE_REQUEST.getUnit(),
        RENEW_CERTIFICATE_REQUEST.getCareUnit(),
        RENEW_CERTIFICATE_REQUEST.getCareProvider()
    );

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(renewCertificateDomainService).renew(
        new CertificateId(CERTIFICATE_ID),
        actionEvaluation,
        new ExternalReference(EXTERNAL_REF)
    );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);

    doReturn(expectedResponse.getCertificate()).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);

    final var actualResponse = renewCertificateService.renew(RENEW_CERTIFICATE_REQUEST,
        CERTIFICATE_ID);
    assertEquals(expectedResponse, actualResponse);
  }
}