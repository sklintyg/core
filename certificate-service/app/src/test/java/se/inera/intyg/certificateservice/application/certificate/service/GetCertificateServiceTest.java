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
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;

@ExtendWith(MockitoExtension.class)
class GetCertificateServiceTest {

  private static final UserDTO USER_DTO = UserDTO.builder().build();
  private static final PatientDTO PATIENT_DTO = PatientDTO.builder().build();
  private static final UnitDTO UNIT_DTO = UnitDTO.builder().build();
  private static final UnitDTO CARE_UNIT_DTO = UnitDTO.builder().build();
  private static final UnitDTO CARE_PROVIDER_DTO = UnitDTO.builder().build();
  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private GetCertificateRequestValidator getCertificateRequestValidator;
  @Mock
  private GetCertificateDomainService getCertificateDomainService;
  @Mock
  private CertificateConverter certificateConverter;
  @InjectMocks
  private GetCertificateService getCertificateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = GetCertificateRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(getCertificateRequestValidator)
        .validate(request, CERTIFICATE_ID);
    assertThrows(IllegalArgumentException.class,
        () -> getCertificateService.get(request, CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnResponseWithCertificate() {
    final var certificateDTO = CertificateDTO.builder().build();
    final var expectedResponse = GetCertificateResponse.builder()
        .certificate(
            certificateDTO
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        PATIENT_DTO,
        USER_DTO,
        UNIT_DTO,
        CARE_UNIT_DTO,
        CARE_PROVIDER_DTO
    );

    final var certificate = Certificate.builder().build();
    doReturn(certificate).when(getCertificateDomainService).get(
        new CertificateId(CERTIFICATE_ID),
        actionEvaluation
    );

    doReturn(certificateDTO).when(certificateConverter).convert(certificate);

    final var actualResult = getCertificateService.get(
        GetCertificateRequest.builder()
            .user(USER_DTO)
            .patient(PATIENT_DTO)
            .unit(UNIT_DTO)
            .careUnit(CARE_UNIT_DTO)
            .careProvider(CARE_PROVIDER_DTO)
            .build(),
        CERTIFICATE_ID
    );

    assertEquals(expectedResponse, actualResult);
  }
}
