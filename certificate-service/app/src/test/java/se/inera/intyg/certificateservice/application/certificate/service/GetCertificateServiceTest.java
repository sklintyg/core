package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;

@ExtendWith(MockitoExtension.class)
class GetCertificateServiceTest {

  @Mock
  private GetCertificateRequestValidator getCertificateRequestValidator;
  @InjectMocks
  private GetCertificateService getCertificateService;

  @Test
  void shouldThrowIfRequestIsInvalid() {
    final var request = GetCertificateRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(getCertificateRequestValidator).validate(request);
    assertThrows(IllegalArgumentException.class, () -> getCertificateService.get(request));
  }
}