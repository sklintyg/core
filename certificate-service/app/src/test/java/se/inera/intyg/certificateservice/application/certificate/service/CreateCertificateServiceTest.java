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
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.CreateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

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
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
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
      .user(AJLA_DOCTOR_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .patient(ATHENA_REACT_ANDERSSON_DTO)
      .externalReference(EXTERNAL_REF)
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
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var expectedReponse = CreateCertificateResponse.builder()
        .certificate(
            CertificateDTO.builder()
                .links(List.of(resourceLinkDTO))
                .build()
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

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(createCertificateDomainService).create(
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build(),
        actionEvaluation,
        new ExternalReference(EXTERNAL_REF),
        null
    );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);

    doReturn(expectedReponse.getCertificate()).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);

    final var actualResponse = createCertificateService.create(CREATE_CERTIFICATE_REQUEST);
    assertEquals(expectedReponse, actualResponse);
  }
}