package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.DisposeObsoleteDraftsDomainService;

@ExtendWith(MockitoExtension.class)
class DisposeObsoleteDraftsInternalServiceTest {

  @Mock
  DisposeObsoleteDraftsDomainService disposeObsoleteDraftsDomainService;
  @Mock
  CertificateConverter converter;
  @InjectMocks
  DisposeObsoleteDraftsInternalService disposeObsoleteDraftsInternalService;

  @Test
  void shouldThrowIfCutoffDateIsNullForList() {
    final var request = ListObsoleteDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> disposeObsoleteDraftsInternalService.list(request));
  }

  @Test
  void shouldReturnListObsoleteDraftsResponse() {
    final var certificateId = "cert-123";
    final var expectedResponse = ListObsoleteDraftsResponse.builder()
        .certificateIds(List.of(certificateId))
        .build();
    final var cutoffDate = LocalDateTime.now();
    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    doReturn(List.of(new CertificateId(certificateId))).when(disposeObsoleteDraftsDomainService)
        .list(cutoffDate);

    final var actualResponse = disposeObsoleteDraftsInternalService.list(request);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void shouldReturnEmptyListWhenNoObsoleterafts() {
    final var expectedResponse = ListObsoleteDraftsResponse.builder()
        .certificateIds(Collections.emptyList())
        .build();
    final var cutoffDate = LocalDateTime.now();
    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    doReturn(Collections.emptyList()).when(disposeObsoleteDraftsDomainService).list(cutoffDate);

    final var actualResponse = disposeObsoleteDraftsInternalService.list(request);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void shouldThrowIfCertificateIdIsNullForDelete() {
    final var request = DisposeObsoleteDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> disposeObsoleteDraftsInternalService.delete(request));
  }

  @Test
  void shouldReturnDisposeObsoleteDraftsResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResponse = DisposeObsoleteDraftsResponse.builder()
        .certificate(expectedCertificate)
        .build();
    final var certificateId = new CertificateId("cert-123");
    final var request = DisposeObsoleteDraftsRequest.builder()
        .certificateId("cert-123")
        .build();

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(disposeObsoleteDraftsDomainService).delete(certificateId);
    doReturn(expectedCertificate).when(converter)
        .convert(certificate, Collections.emptyList(), null);

    final var actualResponse = disposeObsoleteDraftsInternalService.delete(request);
    assertEquals(expectedResponse, actualResponse);
  }
}

