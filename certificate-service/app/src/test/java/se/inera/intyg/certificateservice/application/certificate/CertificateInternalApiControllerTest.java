package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalMetadataService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalXmlService;
import se.inera.intyg.certificateservice.application.certificate.service.LockOldDraftsInternalService;

@ExtendWith(MockitoExtension.class)
class CertificateInternalApiControllerTest {

  @Mock
  private LockOldDraftsInternalService lockOldDraftsInternalService;
  @Mock
  private CertificateExistsService certificateExistsService;
  @Mock
  private GetCertificateInternalMetadataService getCertificateInternalMetadataService;
  @Mock
  private GetCertificateInternalService getCertificateInternalService;
  @Mock
  private GetCertificateInternalXmlService getCertificateInternalXmlService;
  @InjectMocks
  private CertificateInternalApiController certificateInternalApiController;

  @Test
  void shallReturnTrueIfCertificateExists() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(true)
        .build();

    doReturn(expectedResult).when(certificateExistsService).exist(CERTIFICATE_ID);

    final var actualResult = certificateInternalApiController.findExistingCertificate(
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateXmlResponse() {
    final var expectedResult = GetCertificateInternalXmlResponse.builder()
        .certificateId(CERTIFICATE_ID)
        .xml("XML")
        .build();

    doReturn(expectedResult).when(getCertificateInternalXmlService).get(CERTIFICATE_ID);

    final var actualResult = certificateInternalApiController.getCertificateXml(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateMetadataResponse() {
    final var expectedResult = GetCertificateInternalMetadataResponse.builder()
        .certificateMetadata(CertificateMetadataDTO.builder().build())
        .build();

    doReturn(expectedResult).when(getCertificateInternalMetadataService).get(CERTIFICATE_ID);

    final var actualResult = certificateInternalApiController.getCertificateMetadata(
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateResponse() {
    final var expectedResult = GetCertificateInternalResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    doReturn(expectedResult).when(getCertificateInternalService).get(CERTIFICATE_ID);

    final var actualResult = certificateInternalApiController.getCertificate(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnLockOldDraftsResponse() {
    final var expectedResult = LockOldDraftsResponse.builder().build();
    final var request = LockOldDraftsRequest.builder().build();
    doReturn(expectedResult).when(lockOldDraftsInternalService).lock(request);

    final var actualResult = certificateInternalApiController.lockOldDrafts(request);
    assertEquals(expectedResult, actualResult);
  }
}
