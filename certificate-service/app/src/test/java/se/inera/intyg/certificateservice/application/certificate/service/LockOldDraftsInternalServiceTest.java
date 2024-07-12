package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.SetCertificatesToLockedDomainService;

@ExtendWith(MockitoExtension.class)
class LockOldDraftsInternalServiceTest {

  @Mock
  CertificateRepository certificateRepository;
  @Mock
  SetCertificatesToLockedDomainService setCertificatesToLockedDomainService;
  @Mock
  CertificateConverter converter;
  @InjectMocks
  LockOldDraftsInternalService lockOldDraftsInternalService;

  @Test
  void shallThrowIfCutoffDateIsNull() {
    final var request = LockOldDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class, () -> lockOldDraftsInternalService.lock(request));
  }

  @Test
  void shallCallCertificateRepositoryWithCutoffDateFromRequest() {
    final var expectedDate = LocalDateTime.now();
    final var request = LockOldDraftsRequest.builder()
        .cutoffDate(expectedDate)
        .build();

    final var localDateTimeArgumentCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
    lockOldDraftsInternalService.lock(request);
    verify(certificateRepository).draftsCreatedBefore(localDateTimeArgumentCaptor.capture());
    assertEquals(expectedDate, localDateTimeArgumentCaptor.getValue());
  }

  @Test
  void shallCallSetCertificatesToLockedDomainServiceWithCertificates() {
    final var request = LockOldDraftsRequest.builder()
        .cutoffDate(LocalDateTime.now())
        .build();

    final var certificate = mock(Certificate.class);
    final var certificates = List.of(certificate);
    doReturn(certificates).when(certificateRepository)
        .draftsCreatedBefore(request.getCutoffDate());

    lockOldDraftsInternalService.lock(request);

    verify(setCertificatesToLockedDomainService).lock(certificates);
  }

  @Test
  void shallReturnLockOldDraftsResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResponse = LockOldDraftsResponse.builder()
        .certificates(List.of(expectedCertificate))
        .build();
    final var request = LockOldDraftsRequest.builder()
        .cutoffDate(LocalDateTime.now())
        .build();

    final var certificate = mock(Certificate.class);
    final var certificates = List.of(certificate);
    doReturn(certificates).when(certificateRepository)
        .draftsCreatedBefore(request.getCutoffDate());
    doReturn(expectedCertificate).when(converter).convert(certificate, Collections.emptyList());

    final var actualResponse = lockOldDraftsInternalService.lock(request);
    assertEquals(expectedResponse, actualResponse);
  }
}
