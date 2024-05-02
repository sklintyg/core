package se.inera.intyg.certificateservice.application.certificateexternaltypeinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionRequest;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.service.GetLatestCertificateExternalTypeVersionService;

@ExtendWith(MockitoExtension.class)
class CertificateExternalTypeInfoControllerTest {

  @Mock
  private GetLatestCertificateExternalTypeVersionService getLatestCertificateExternalTypeVersionService;
  @InjectMocks
  private CertificateExternalTypeInfoController certificateExternalTypeInfoController;

  @Test
  void shallReturnGetLatestCertificateExternalTypeVersionResponse() {
    final var request = GetLatestCertificateExternalTypeVersionRequest.builder().build();
    final var expectedResponse = GetLatestCertificateExternalTypeVersionResponse.builder().build();

    doReturn(expectedResponse).when(getLatestCertificateExternalTypeVersionService).get(request);

    final var actualResponse = certificateExternalTypeInfoController.findLatestCertificateExternalTypeVersion(
        request);

    assertEquals(expectedResponse, actualResponse);
  }
}