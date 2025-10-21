package se.inera.intyg.certificateservice.application.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.service.validator.GetUnitCertificatesRequestValidator;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesDomainService;

@ExtendWith(MockitoExtension.class)
class GetUnitCertificatesServiceTest {

  @Mock
  private GetUnitCertificatesRequestValidator getUnitCertificatesRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private GetUnitCertificatesDomainService getUnitCertificatesDomainService;
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
  @Mock
  private CertificateConverter certificateConverter;
  @InjectMocks
  private GetUnitCertificatesService getUnitCertificatesService;

  private static final CertificatesQueryCriteriaDTO QUERY_CRITERIA_DTO = CertificatesQueryCriteriaDTO.builder()
      .build();

  @Test
  void shallThrowIfInvalidRequest() {
    final var request = GetUnitCertificatesRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(getUnitCertificatesRequestValidator)
        .validate(request);

    assertThrows(IllegalArgumentException.class, () -> getUnitCertificatesService.get(request));
  }

  @Test
  void shallReturnGetUnitCertificatesResponse() {
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var certificateDTO = CertificateDTO.builder()
        .links(List.of(resourceLinkDTO))
        .build();
    final var expectedResponse = GetUnitCertificatesResponse.builder()
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
    doReturn(List.of(certificate)).when(getUnitCertificatesDomainService).get(
        any(CertificatesRequest.class),
        eq(actionEvaluation)
    );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);
    doReturn(certificateDTO).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);

    final var actualResult = getUnitCertificatesService.get(
        GetUnitCertificatesRequest.builder()
            .patient(ATHENA_REACT_ANDERSSON_DTO)
            .user(AJLA_DOCTOR_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .certificatesQueryCriteria(QUERY_CRITERIA_DTO)
            .build()
    );

    assertEquals(expectedResponse, actualResult);
  }
}