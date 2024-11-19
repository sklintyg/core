package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificatePdfRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificatePdfDomainService;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.user.model.User;

@ExtendWith(MockitoExtension.class)
class GetCertificatePdfServiceTest {

  private static final String FILE_NAME = "fileName";
  private static final byte[] PDF_DATA = "pdf".getBytes();
  private static final Pdf PDF = new Pdf(PDF_DATA, FILE_NAME);

  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";
  private static final GetCertificatePdfRequest REQUEST = GetCertificatePdfRequest.builder()
      .patient(ATHENA_REACT_ANDERSSON_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .user(AJLA_DOCTOR_DTO)
      .additionalInfoText(ADDITIONAL_INFO_TEXT)
      .build();

  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private GetCertificatePdfDomainService getCertificatePdfDomainService;
  @Mock
  private GetCertificatePdfRequestValidator getCertificatePdfRequestValidator;
  @InjectMocks
  GetCertificatePdfService getCertificatePdfService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = GetCertificatePdfRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(getCertificatePdfRequestValidator)
        .validate(request, CERTIFICATE_ID);

    assertThrows(IllegalArgumentException.class,
        () -> getCertificatePdfService.get(request, CERTIFICATE_ID)
    );
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setup() {
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

      doReturn(PDF).when(getCertificatePdfDomainService).get(
          new CertificateId(CERTIFICATE_ID),
          actionEvaluation,
          ADDITIONAL_INFO_TEXT
      );
    }

    @Test
    void shallReturnResponseWithPdf() {
      final var expectedResponse = GetCertificatePdfResponse.builder()
          .fileName(FILE_NAME)
          .pdfData(PDF_DATA)
          .build();

      final var actualResult = getCertificatePdfService.get(
          REQUEST,
          CERTIFICATE_ID
      );

      assertEquals(expectedResponse, actualResult);
    }
  }
}
