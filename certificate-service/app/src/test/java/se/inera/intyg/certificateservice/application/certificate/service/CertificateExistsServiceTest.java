package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class CertificateExistsServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  private CertificateRepository certificateRepository;

  @InjectMocks
  private CertificateExistsService certificateExistsService;

  @Test
  void shouldThrowIfCertificateIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> certificateExistsService.exist(null));
  }

  @Test
  void shouldThrowIfCertificateIdIsEmpty() {
    assertThrows(IllegalArgumentException.class, () -> certificateExistsService.exist(""));
  }

  @Test
  void shouldReturnTrueIfCertificateIsFound() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(true)
        .build();

    doReturn(true).when(certificateRepository).exists(new CertificateId(CERTIFICATE_ID));

    final var actualResult = certificateExistsService.exist(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shouldReturnFalseIfCertificateIsNotFound() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(false)
        .build();

    doReturn(false).when(certificateRepository)
        .exists(new CertificateId(CERTIFICATE_ID));

    final var actualResult = certificateExistsService.exist(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }
}
