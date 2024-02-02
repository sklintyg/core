package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;

@ExtendWith(MockitoExtension.class)
class CreateCertificateServiceTest {

  @Mock
  private CreateCertificateRequestValidator createCertificateRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private CreateCertificateDomainService createCertificateDomainService;
  @Mock
  private CertificateConverter certificateConverter;
  @InjectMocks
  private CreateCertificateService createCertificateService;

  private static final String TYPE = "type";
  private static final String VERSION = "version";
  private static final CreateCertificateRequest CREATE_CERTIFICATE_REQUEST = CreateCertificateRequest.builder()
      .certificateModelId(
          CertificateModelIdDTO.builder()
              .type(TYPE)
              .version(VERSION)
              .build()
      )
      .careProvider(UnitDTO.builder().build())
      .careUnit(UnitDTO.builder().build())
      .unit(UnitDTO.builder().build())
      .patient(PatientDTO.builder().build())
      .build();

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = CreateCertificateRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(createCertificateRequestValidator)
        .validate(request);

    assertThrows(IllegalArgumentException.class,
        () -> createCertificateService.create(request)
    );
  }

  @Test
  void shallReturnResponseWithCreatedCertificate() {
    final var expectedReponse = CreateCertificateResponse.builder()
        .certificate(
            CertificateDTO.builder().build()
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        CREATE_CERTIFICATE_REQUEST.getPatient(),
        CREATE_CERTIFICATE_REQUEST.getUser(),
        CREATE_CERTIFICATE_REQUEST.getUnit(),
        CREATE_CERTIFICATE_REQUEST.getCareUnit(),
        CREATE_CERTIFICATE_REQUEST.getCareProvider()
    );

    final var certificate = Certificate.builder().build();
    doReturn(certificate).when(createCertificateDomainService).create(
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build(),
        actionEvaluation
    );

    doReturn(expectedReponse.getCertificate()).when(certificateConverter).convert(certificate);

    final var actualResponse = createCertificateService.create(CREATE_CERTIFICATE_REQUEST);
    assertEquals(expectedReponse, actualResponse);
  }
}
