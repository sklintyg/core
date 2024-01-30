package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;

@ExtendWith(MockitoExtension.class)
class CreateCertificateServiceTest {

  @Mock
  private CreateCertificateRequestValidator createCertificateRequestValidator;
  @InjectMocks
  private CreateCertificateService createCertificateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = CreateCertificateRequest.builder().build();
    
    doThrow(IllegalArgumentException.class).when(
        createCertificateRequestValidator).validate(request);

    assertThrows(IllegalArgumentException.class,
        () -> createCertificateService.create(request));
  }
}
