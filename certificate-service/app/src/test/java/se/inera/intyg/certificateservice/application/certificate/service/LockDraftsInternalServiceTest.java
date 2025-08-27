package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.LockCertificateDomainService;

@ExtendWith(MockitoExtension.class)
class LockDraftsInternalServiceTest {

  @Mock
  LockCertificateDomainService lockCertificateDomainService;
  @Mock
  CertificateConverter converter;
  @InjectMocks
  LockDraftsInternalService lockDraftsInternalService;

  @Test
  void shallThrowIfCutoffDateIsNull() {
    final var request = LockDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class, () -> lockDraftsInternalService.lock(request));
  }

  @Test
  void shallReturnLockOldDraftsResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResponse = LockDraftsResponse.builder()
        .certificates(List.of(expectedCertificate))
        .build();
    final var request = LockDraftsRequest.builder()
        .cutoffDate(LocalDateTime.now())
        .build();

    final var certificate = mock(MedicalCertificate.class);
    final var certificates = List.of(certificate);
    doReturn(certificates).when(lockCertificateDomainService).lock(request.getCutoffDate());
    doReturn(expectedCertificate).when(converter)
        .convert(certificate, Collections.emptyList(), null);

    final var actualResponse = lockDraftsInternalService.lock(request);
    assertEquals(expectedResponse, actualResponse);
  }
}