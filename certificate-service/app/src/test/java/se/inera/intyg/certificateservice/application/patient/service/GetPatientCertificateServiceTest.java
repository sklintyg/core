package se.inera.intyg.certificateservice.application.patient.service;

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
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.application.patient.service.validator.GetPatientCertificatesRequestValidator;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesDomainService;

@ExtendWith(MockitoExtension.class)
class GetPatientCertificateServiceTest {

  @Mock
  private GetPatientCertificatesRequestValidator getPatientCertificatesRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;

  @Mock
  private GetPatientCertificatesDomainService getPatientCertificatesDomainService;

  @Mock
  private ResourceLinkConverter resourceLinkConverter;

  @Mock
  private CertificateConverter certificateConverter;
  @InjectMocks
  private GetPatientCertificateService getPatientCertificateService;

  @Test
  void shallThrowIfInvalidRequest() {
    final var request = GetPatientCertificatesRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(getPatientCertificatesRequestValidator)
        .validate(request);

    assertThrows(IllegalArgumentException.class, () -> getPatientCertificateService.get(request));
  }

  @Test
  void shallReturnGetPatientCertificatesResponse() {
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var certificateDTO = CertificateDTO.builder()
        .links(List.of(resourceLinkDTO))
        .build();
    final var expectedResponse = GetPatientCertificatesResponse.builder()
        .certificates(
            List.of(certificateDTO)
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        ATHENA_REACT_ANDERSSON_DTO,
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var certificate = mock(MedicalCertificate.class);
    doReturn(List.of(certificate)).when(getPatientCertificatesDomainService).get(
        actionEvaluation
    );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);
    doReturn(certificateDTO).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);

    final var actualResult = getPatientCertificateService.get(
        GetPatientCertificatesRequest.builder()
            .patient(ATHENA_REACT_ANDERSSON_DTO)
            .user(AJLA_DOCTOR_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .build()
    );

    assertEquals(expectedResponse, actualResult);
  }
}