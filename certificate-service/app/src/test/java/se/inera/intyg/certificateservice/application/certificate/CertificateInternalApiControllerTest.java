package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalXmlService;

@ExtendWith(MockitoExtension.class)
class CertificateInternalApiControllerTest {

  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  private GetCertificateInternalXmlService getCertificateInternalXmlService;
  @InjectMocks
  private CertificateInternalApiController certificateInternalApiController;

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
}
