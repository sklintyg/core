package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportCertificateInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificateInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetValidSickLeaveCertificateIdsInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetValidSickLeaveCertificateIdsInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.TotalExportsInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.EraseCertificateInternalForCareProviderService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateExportsInternalForCareProviderService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalMetadataService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalXmlService;
import se.inera.intyg.certificateservice.application.certificate.service.GetSickLeaveCertificateInternalService;
import se.inera.intyg.certificateservice.application.certificate.service.GetTotalExportsInternalForCareProviderService;
import se.inera.intyg.certificateservice.application.certificate.service.GetValidSickLeaveCertificatesInternalService;
import se.inera.intyg.certificateservice.application.certificate.service.LockDraftsInternalService;
import se.inera.intyg.certificateservice.application.certificate.service.PlaceholderCertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.RevokePlaceholderCertificateInternalService;
import se.inera.intyg.certificateservice.application.patient.service.GetCertificatesWithQAInternalService;

@ExtendWith(MockitoExtension.class)
class CertificateInternalApiControllerTest {

  @Mock
  private GetValidSickLeaveCertificatesInternalService getValidSickLeaveCertificatesInternalService;
  @Mock
  PlaceholderCertificateExistsService placeholderCertificateExistsService;
  @Mock
  RevokePlaceholderCertificateInternalService revokePlaceholderCertificateInternalService;
  @Mock
  private EraseCertificateInternalForCareProviderService eraseCertificateInternalForCareProviderService;
  @Mock
  private GetCertificateExportsInternalForCareProviderService getCertificateExportsInternalForCareProviderService;
  @Mock
  private GetTotalExportsInternalForCareProviderService getTotalExportsInternalForCareProviderService;
  @Mock
  private GetCertificatesWithQAInternalService getCertificatesWithQAInternalService;
  @Mock
  private LockDraftsInternalService lockDraftsInternalService;
  @Mock
  private CertificateExistsService certificateExistsService;
  @Mock
  private GetCertificateInternalMetadataService getCertificateInternalMetadataService;
  @Mock
  private GetCertificateInternalService getCertificateInternalService;
  @Mock
  private GetSickLeaveCertificateInternalService getSickLeaveCertificateInternalService;
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
  void shallReturnGetSickLeaveCertificateResponseIgnoreModelRulesIsFalse() {
    final var request = GetSickLeaveCertificateInternalRequest.builder()
        .ignoreModelRules(false)
        .build();
    final var expectedResult = GetSickLeaveCertificateInternalResponse.builder()
        .sickLeaveCertificate(SickLeaveCertificateDTO.builder().build())
        .build();

    doReturn(expectedResult).when(getSickLeaveCertificateInternalService)
        .get(CERTIFICATE_ID, false);

    final var actualResult = certificateInternalApiController.getSickLeaveCertificate(
        CERTIFICATE_ID, request);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetSickLeaveCertificateResponseIgnoreModelRulesIsTrue() {
    final var request = GetSickLeaveCertificateInternalRequest.builder()
        .ignoreModelRules(true)
        .build();
    final var expectedResult = GetSickLeaveCertificateInternalResponse.builder()
        .sickLeaveCertificate(SickLeaveCertificateDTO.builder().build())
        .build();

    doReturn(expectedResult).when(getSickLeaveCertificateInternalService)
        .get(CERTIFICATE_ID, true);

    final var actualResult = certificateInternalApiController.getSickLeaveCertificate(
        CERTIFICATE_ID, request);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnLockDraftsResponse() {
    final var expectedResult = LockDraftsResponse.builder().build();
    final var request = LockDraftsRequest.builder().build();
    doReturn(expectedResult).when(lockDraftsInternalService).lock(request);

    final var actualResult = certificateInternalApiController.lockDrafts(request);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateInternalResponse() {
    final var expectedResult = CertificatesWithQAInternalResponse.builder().build();
    final var request = CertificatesWithQAInternalRequest.builder().build();
    doReturn(expectedResult).when(getCertificatesWithQAInternalService).get(request);

    final var actualResult = certificateInternalApiController.getCertificatesWithQA(request);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnExportInternalResponse() {
    final var expectedResult = ExportInternalResponse.builder().build();
    final var internalRequest = ExportCertificateInternalRequest.builder().build();

    doReturn(expectedResult).when(getCertificateExportsInternalForCareProviderService)
        .get(internalRequest, "careProviderId");

    final var actualResult = certificateInternalApiController.getExportCertificatesForCareProvider(
        internalRequest, "careProviderId");
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnTotalExportsInternalResponse() {
    final var expectedResult = TotalExportsInternalResponse.builder().build();

    doReturn(expectedResult).when(getTotalExportsInternalForCareProviderService)
        .get("careProviderId");

    final var actualResult = certificateInternalApiController.getTotalExportsForCareProvider(
        "careProviderId");
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallCallEraseCertificateInternalForCareProviderService() {
    certificateInternalApiController.eraseCertificates("careProviderId");
    verify(eraseCertificateInternalForCareProviderService).erase("careProviderId");
  }

  @Test
  void shouldReturnCertificateExistsResponseForPlaceholder() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(true)
        .build();

    when(placeholderCertificateExistsService.exist(CERTIFICATE_ID)).thenReturn(expectedResult);

    final var actualResult = certificateInternalApiController.findExistingPlaceholderCertificate(
        CERTIFICATE_ID);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shouldRevokePlaceHolderCertificate() {
    certificateInternalApiController.revokePlaceholderCertificate(CERTIFICATE_ID);
    verify(revokePlaceholderCertificateInternalService).revoke(CERTIFICATE_ID);
  }

  @Test
  void shouldReturnGetValidSickLeaveCertificateIdsInternalResponse() {
    final var request = GetValidSickLeaveCertificateIdsInternalRequest.builder().build();
    final var expectedResult = GetValidSickLeaveCertificateIdsInternalResponse.builder().build();

    when(getValidSickLeaveCertificatesInternalService.get(request)).thenReturn(expectedResult);

    final var actualResult = certificateInternalApiController.getValidSickLeaveCertificateIds(
        request
    );
    assertEquals(expectedResult, actualResult);
  }
}