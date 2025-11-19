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
    final var certificateId = "cert-123";
    final var expectedResponse = ListStaleDraftsResponse.builder()
        .certificateIds(List.of(certificateId))
        .build();
    final var cutoffDate = LocalDateTime.now();
    final var request = ListStaleDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    doReturn(List.of(new CertificateId(certificateId))).when(deleteStaleDraftsDomainService)
        .list(cutoffDate);

    final var actualResponse = deleteStaleDraftsInternalService.list(request);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void shouldReturnEmptyListWhenNoStaleDrafts() {
    final var expectedResponse = ListStaleDraftsResponse.builder()
        .certificateIds(Collections.emptyList())
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
  void shouldThrowIfCertificateIdIsNullForDelete() {
    final var request = DeleteStaleDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> deleteStaleDraftsInternalService.delete(request));
  }

  @Test
  void shouldReturnDeleteStaleDraftsResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResponse = DeleteStaleDraftsResponse.builder()
        .certificate(expectedCertificate)
        .build();
    final var certificateId = new CertificateId("cert-123");
    final var request = DeleteStaleDraftsRequest.builder()
        .certificateId("cert-123")
        .build();

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(deleteStaleDraftsDomainService).delete(certificateId);
    doReturn(expectedCertificate).when(converter)
        .convert(certificate, Collections.emptyList(), null);

    final var actualResponse = deleteStaleDraftsInternalService.delete(request);
    assertEquals(expectedResponse, actualResponse);
  }
}

