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
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteStaleDraftsDomainService;

@ExtendWith(MockitoExtension.class)
class DeleteStaleDraftsInternalServiceTest {

  @Mock
  DeleteStaleDraftsDomainService deleteStaleDraftsDomainService;
  @Mock
  CertificateConverter converter;
  @InjectMocks
  DeleteStaleDraftsInternalService deleteStaleDraftsInternalService;

  @Test
  void shouldThrowIfCutoffDateIsNullForList() {
    final var request = ListStaleDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> deleteStaleDraftsInternalService.list(request));
  }

  @Test
  void shouldReturnListStaleDraftsResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResponse = ListStaleDraftsResponse.builder()
        .certificates(List.of(expectedCertificate))
        .build();
    final var cutoffDate = LocalDateTime.now();
    final var request = ListStaleDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var certificate = mock(MedicalCertificate.class);
    final var certificates = List.of(certificate);
    doReturn(certificates).when(deleteStaleDraftsDomainService).list(cutoffDate);
    doReturn(expectedCertificate).when(converter)
        .convert(certificate, Collections.emptyList(), null);

    final var actualResponse = deleteStaleDraftsInternalService.list(request);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void shouldReturnEmptyListWhenNoStaleDrafts() {
    final var expectedResponse = ListStaleDraftsResponse.builder()
        .certificates(Collections.emptyList())
        .build();
    final var cutoffDate = LocalDateTime.now();
    final var request = ListStaleDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    doReturn(Collections.emptyList()).when(deleteStaleDraftsDomainService).list(cutoffDate);

    final var actualResponse = deleteStaleDraftsInternalService.list(request);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void shouldThrowIfCertificateIdsIsNullForDelete() {
    final var request = DeleteStaleDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> deleteStaleDraftsInternalService.delete(request));
  }

  @Test
  void shouldThrowIfCertificateIdsIsEmptyForDelete() {
    final var request = DeleteStaleDraftsRequest.builder()
        .certificateIds(Collections.emptyList())
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> deleteStaleDraftsInternalService.delete(request));
  }

  @Test
  void shouldReturnDeleteStaleDraftsResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResponse = DeleteStaleDraftsResponse.builder()
        .certificates(List.of(expectedCertificate))
        .build();
    final var certificateIds = List.of(new CertificateId("cert-123"),
        new CertificateId("cert-456"));
    final var request = DeleteStaleDraftsRequest.builder()
        .certificateIds(List.of("cert-123", "cert-456"))
        .build();

    final var certificate = mock(MedicalCertificate.class);
    final var certificates = List.of(certificate);
    doReturn(certificates).when(deleteStaleDraftsDomainService).delete(certificateIds);
    doReturn(expectedCertificate).when(converter)
        .convert(certificate, Collections.emptyList(), null);

    final var actualResponse = deleteStaleDraftsInternalService.delete(request);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void shouldReturnMultipleDeletedCertificates() {
    final var certificate1 = mock(MedicalCertificate.class);
    final var certificate2 = mock(MedicalCertificate.class);
    final var certificates = List.of(certificate1, certificate2);
    final var certificateDTO1 = CertificateDTO.builder().build();
    final var certificateDTO2 = CertificateDTO.builder().build();
    final var expectedResponse = DeleteStaleDraftsResponse.builder()
        .certificates(List.of(certificateDTO1, certificateDTO2))
        .build();
    final var certificateIds = List.of(new CertificateId("cert-123"),
        new CertificateId("cert-456"));
    final var request = DeleteStaleDraftsRequest.builder()
        .certificateIds(List.of("cert-123", "cert-456"))
        .build();

    doReturn(certificates).when(deleteStaleDraftsDomainService).delete(certificateIds);
    doReturn(certificateDTO1).when(converter).convert(certificate1, Collections.emptyList(), null);
    doReturn(certificateDTO2).when(converter).convert(certificate2, Collections.emptyList(), null);

    final var actualResponse = deleteStaleDraftsInternalService.delete(request);
    assertEquals(expectedResponse, actualResponse);
  }
}

