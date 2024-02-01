package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;

@ExtendWith(MockitoExtension.class)
class CertificateControllerTest {

  @Mock
  private CreateCertificateService createCertificateService;
  @InjectMocks
  private CertificateController certificateController;

  @Test
  void shallReturnCreateCertificateResponse() {
    final var expectedResult = CreateCertificateResponse.builder()
        .certificate(
            CertificateDTO.builder().build()
        )
        .build();

    final var request = CreateCertificateRequest.builder().build();

    doReturn(expectedResult).when(createCertificateService).create(request);

    final var actualResult = certificateController.createCertificate(request);

    assertEquals(expectedResult, actualResult);
  }
}