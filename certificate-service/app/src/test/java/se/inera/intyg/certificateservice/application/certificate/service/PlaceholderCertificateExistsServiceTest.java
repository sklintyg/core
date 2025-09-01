package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class PlaceholderCertificateExistsServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  PlaceholderCertificateExistsService placeholderCertificateExistsService;

  @Test
  void shouldThrowIfCertificateIdIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> placeholderCertificateExistsService.exist(null));
  }

  @Test
  void shouldThrowIfCertificateIdIsBlank() {
    assertThrows(IllegalArgumentException.class,
        () -> placeholderCertificateExistsService.exist(""));
  }

  @Test
  void shouldReturnCertificateExistsResponse() {
    final var expectedResponse = CertificateExistsResponse.builder()
        .exists(true)
        .build();

    when(certificateRepository.placeholderExists(new CertificateId(CERTIFICATE_ID))).thenReturn(
        true);

    final var exist = placeholderCertificateExistsService.exist(CERTIFICATE_ID);
    assertEquals(expectedResponse, exist);
  }
}