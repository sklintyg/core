package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
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
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateWithoutSignatureRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificatePdfService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateXmlService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateWithoutSignatureService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ValidateCertificateService;

@ExtendWith(MockitoExtension.class)
class CertificateControllerTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final Long VERSION = 0L;

  @Mock
  private ValidateCertificateService validateCertificateService;
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
  @Mock
  private GetCertificateXmlService getCertificateXmlService;
  @Mock
  private SignCertificateService signCertificateService;
  @Mock
  private SignCertificateWithoutSignatureService signCertificateWithoutSignatureService;
  @Mock
  private GetCertificatePdfService getCertificatePdfService;
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

  @Test
  void shallReturnValidateCertificateResponse() {
    final var request = ValidateCertificateRequest.builder().build();
    final var expectedResult = ValidateCertificateResponse.builder()
        .validationErrors(Collections.emptyList())
        .build();

    doReturn(expectedResult).when(validateCertificateService).validate(request, CERTIFICATE_ID);

    final var actualResult = certificateController.validateCertificate(request,
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateXmlResponse() {
    final var request = GetCertificateXmlRequest.builder().build();
    final var expectedResult = GetCertificateXmlResponse.builder()
        .xml("XML")
        .build();
    doReturn(expectedResult).when(getCertificateXmlService).get(request, CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificateXml(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnSignCertificateResponse() {
    final var request = SignCertificateRequest.builder().build();
    final var expectedResult = SignCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();
    doReturn(expectedResult).when(signCertificateService).sign(request, CERTIFICATE_ID, VERSION);

    final var actualResult = certificateController.signCertificate(request, CERTIFICATE_ID,
        VERSION);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnSignCertificateResponseWhenSigningWithoutSignature() {
    final var request = SignCertificateWithoutSignatureRequest.builder().build();
    final var expectedResult = SignCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();
    doReturn(expectedResult).when(signCertificateWithoutSignatureService)
        .sign(request, CERTIFICATE_ID, VERSION);

    final var actualResult = certificateController.signCertificateWithoutSignature(request,
        CERTIFICATE_ID, VERSION);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificatePdfResponse() {
    final var request = GetCertificatePdfRequest.builder().build();
    final var expectedResult = GetCertificatePdfResponse.builder()
        .pdfData("pdf".getBytes())
        .fileName("fileName")
        .build();
    doReturn(expectedResult).when(getCertificatePdfService).get(request, CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificatePdf(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }
}
