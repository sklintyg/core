package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateMetadataConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class GetCertificateInternalMetadataServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CertificateMetadataConverter certificateMetadataConverter;
  @InjectMocks
  GetCertificateInternalMetadataService getCertificateInternalMetadataService;

  @Test
  void shallThrowIfCertificateIdIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> getCertificateInternalMetadataService.get(null));
  }

  @Test
  void shallThrowIfCertificateIdIsBlank() {
    assertThrows(IllegalArgumentException.class,
        () -> getCertificateInternalMetadataService.get(""));
  }

  @Test
  void shallReturnGetCertificateMetadataResponse() {
    final var metadata = CertificateMetadataDTO.builder().build();
    final var certificate = MedicalCertificate.builder().build();
    final var expectedResponse = GetCertificateInternalMetadataResponse.builder()
        .certificateMetadata(metadata)
        .build();

    doReturn(certificate).when(certificateRepository).getById(new CertificateId(CERTIFICATE_ID));
    doReturn(metadata).when(certificateMetadataConverter).convert(certificate, null);
    final var actualResponse = getCertificateInternalMetadataService.get(CERTIFICATE_ID);
    assertEquals(expectedResponse, actualResponse);
  }
}