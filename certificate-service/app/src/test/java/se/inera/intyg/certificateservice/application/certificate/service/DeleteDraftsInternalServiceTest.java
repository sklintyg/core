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
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteDraftsDomainService;

@ExtendWith(MockitoExtension.class)
class DeleteDraftsInternalServiceTest {

  @Mock
  DeleteDraftsDomainService deleteDraftsDomainService;
  @Mock
  CertificateConverter converter;
  @InjectMocks
  DeleteDraftsInternalService deleteDraftsInternalService;

  @Test
  void shouldThrowIfCutoffDateIsNull() {
    final var request = DeleteDraftsRequest.builder()
        .build();
    assertThrows(IllegalArgumentException.class, () -> deleteDraftsInternalService.delete(request));
  }

  @Test
  void shouldReturnDeleteDraftsResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResponse = DeleteDraftsResponse.builder()
        .certificates(List.of(expectedCertificate))
        .build();
    final var request = DeleteDraftsRequest.builder()
        .cutoffDate(LocalDateTime.now())
        .build();

    final var certificate = mock(MedicalCertificate.class);
    final var certificates = List.of(certificate);
    doReturn(certificates).when(deleteDraftsDomainService).delete(request.getCutoffDate());
    doReturn(expectedCertificate).when(converter)
        .convert(certificate, Collections.emptyList(), null);

    final var actualResponse = deleteDraftsInternalService.delete(request);
    assertEquals(expectedResponse, actualResponse);
  }
}

