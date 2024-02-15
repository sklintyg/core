package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateCertificateService;

@ExtendWith(MockitoExtension.class)
class CertificateControllerTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final Long VERSION = 0L;
  @Mock
  private UpdateCertificateService updateCertificateService;
  @Mock
  private GetCertificateService getCertificateService;
  @Mock
  private CertificateExistsService certificateExistsService;
  @Mock
  private CreateCertificateService createCertificateService;
  @Mock
  private DeleteCertificateService deleteCertificateService;
  @InjectMocks
  private CertificateController certificateController;

  @Test
  void shallReturnCreateCertificateResponse() {
    final var expectedResult = CreateCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    final var request = CreateCertificateRequest.builder().build();

    doReturn(expectedResult).when(createCertificateService).create(
        request
    );

    final var actualResult = certificateController.createCertificate(request);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnTrueIfCertificateExists() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(true)
        .build();

    doReturn(expectedResult).when(certificateExistsService).exist(CERTIFICATE_ID);

    final var actualResult = certificateController.findExistingCertificate(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnFalseIfCertificateDontExists() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(false)
        .build();

    doReturn(expectedResult).when(certificateExistsService).exist(CERTIFICATE_ID);

    final var actualResult = certificateController.findExistingCertificate(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateResponse() {
    final var expectedResult = GetCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    final var request = GetCertificateRequest.builder().build();

    doReturn(expectedResult).when(getCertificateService).get(
        request,
        CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnUpdateCertificateResponse() {
    final var expectedResult = UpdateCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    final var request = UpdateCertificateRequest.builder().build();

    doReturn(expectedResult).when(updateCertificateService).update(request, CERTIFICATE_ID);

    final var actualResult = certificateController.updateCertificate(request,
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnDeleteCertificateResponse() {
    final var request = DeleteCertificateRequest.builder().build();
    final var expectedResult = DeleteCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();
    doReturn(expectedResult).when(deleteCertificateService)
        .delete(request, CERTIFICATE_ID, VERSION);

    final var actualResult = certificateController.deleteCertificate(request, CERTIFICATE_ID,
        VERSION);

    assertEquals(expectedResult, actualResult);
  }
}
