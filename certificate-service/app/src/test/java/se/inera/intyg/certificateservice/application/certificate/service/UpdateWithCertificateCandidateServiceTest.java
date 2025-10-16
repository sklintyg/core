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

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateWithCertificateCandidateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateWithCertificateCandidateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.user.model.User;

@ExtendWith(MockitoExtension.class)
class UpdateWithCertificateCandidateServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String CANDIDATE_CERTIFICATE_ID = "candidateCertificateId";
  private static final String EXTERNAL_REFERENCE = "externalReference";
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private UpdateWithCertificateCandidateRequestValidator updateWithCertificateCandidateRequestValidator;
  @Mock
  private UpdateWithCertificateCandidateDomainService updateWithCertificateCandidateDomainService;
  @Mock
  private CertificateConverter certificateConverter;
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
  @InjectMocks
  private UpdateWithCertificateCandidateService updateWithCertificateCandidateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = UpdateWithCertificateCandidateRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(updateWithCertificateCandidateRequestValidator)
        .validate(request, CERTIFICATE_ID, CANDIDATE_CERTIFICATE_ID);
    assertThrows(IllegalArgumentException.class,
        () -> updateWithCertificateCandidateService.update(request, CERTIFICATE_ID,
            CANDIDATE_CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnResponseWithCertificate() {
    final var resourceLinkDTO = ResourceLinkDTO.builder()
        .type(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
        .build();

    final var certificateDTO = CertificateDTO.builder()
        .links(List.of(resourceLinkDTO))
        .build();
    final var expectedResponse = UpdateWithCertificateCandidateResponse.builder()
        .certificate(
            certificateDTO
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder()
        .user(User.builder().role(Role.DOCTOR).build())
        .build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        ATHENA_REACT_ANDERSSON_DTO,
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate)
        .when(updateWithCertificateCandidateDomainService).update(
            new CertificateId(CERTIFICATE_ID),
            new CertificateId(CANDIDATE_CERTIFICATE_ID),
            actionEvaluation,
            new ExternalReference(EXTERNAL_REFERENCE)
        );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);
    doReturn(certificateDTO).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);

    final var actualResult = updateWithCertificateCandidateService.update(
        UpdateWithCertificateCandidateRequest.builder()
            .patient(ATHENA_REACT_ANDERSSON_DTO)
            .user(AJLA_DOCTOR_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .externalReference(EXTERNAL_REFERENCE)
            .build(),
        CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID
    );

    assertEquals(expectedResponse, actualResult);
  }
}