package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonCertificateDTO.certificate;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.QUESTION_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;

@ExtendWith(MockitoExtension.class)
class UpdateCertificateServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final CertificateDTO CERTIFICATE = certificate().build();
  @Mock
  private UpdateCertificateRequestValidator updateCertificateRequestValidator;

  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private ElementDataConverter elementDataConverter;

  @Mock
  private UpdateCertificateDomainService updateCertificateDomainService;

  @Mock
  private CertificateConverter certificateConverter;

  @InjectMocks
  private UpdateCertificateService updateCertificateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = UpdateCertificateRequest.builder().build();
    doThrow(IllegalStateException.class).when(updateCertificateRequestValidator).validate(
        request,
        CERTIFICATE_ID
    );
    assertThrows(IllegalStateException.class,
        () -> updateCertificateService.update(request, CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnUpdateCertificateResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResult = UpdateCertificateResponse.builder()
        .certificate(expectedCertificate)
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );
    final var elementData = ElementData.builder().build();

    doReturn(elementData).when(elementDataConverter)
        .convert(QUESTION_ID, CERTIFICATE.getData().get(QUESTION_ID));

    final var certificate = Certificate.builder().build();

    doReturn(certificate).when(updateCertificateDomainService).update(
        new CertificateId(CERTIFICATE_ID), List.of(elementData), actionEvaluation
    );

    doReturn(CertificateDTO.builder().build()).when(certificateConverter).convert(certificate);

    final var actualResult = updateCertificateService.update(
        UpdateCertificateRequest.builder()
            .user(AJLA_DOCTOR_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .certificate(CERTIFICATE)
            .build(),
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }
}
